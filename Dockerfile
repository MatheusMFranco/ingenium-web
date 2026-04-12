# ── Stage 1: Build ──────────────────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /build
COPY INGENIUM/pom.xml ./pom.xml
RUN mvn -DskipTests dependency:go-offline --no-transfer-progress

COPY INGENIUM/src    ./src
RUN mvn clean package -DskipTests --no-transfer-progress

# ── Stage 2: WildFly 38 ──────────────────────────────────────────────────────
FROM quay.io/wildfly/wildfly:38.0.0.Final-jdk17

USER root
RUN mkdir -p /opt/jboss/wildfly/modules/com/mysql/main /opt/jboss/wildfly/customization

COPY --from=build /root/.m2/repository/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar \
     /opt/jboss/wildfly/modules/com/mysql/main/mysql-connector-j.jar
COPY docker/wildfly/module.xml /opt/jboss/wildfly/modules/com/mysql/main/module.xml
COPY docker/wildfly/configure-datasource.cli /opt/jboss/wildfly/customization/configure-datasource.cli

RUN /opt/jboss/wildfly/bin/jboss-cli.sh --file=/opt/jboss/wildfly/customization/configure-datasource.cli \
    && rm -rf /opt/jboss/wildfly/standalone/configuration/standalone_xml_history \
    && mkdir -p /opt/jboss/wildfly/standalone/configuration/standalone_xml_history/current \
    && chown -R jboss:root /opt/jboss/wildfly/standalone \
    && chown -R jboss:root /opt/jboss/wildfly/modules/com/mysql \
    && chown -R jboss:root /opt/jboss/wildfly/customization

COPY --from=build /build/target/INGENIUM.war \
     /opt/jboss/wildfly/standalone/deployments/INGENIUM.war

USER jboss

EXPOSE 8080 9990
