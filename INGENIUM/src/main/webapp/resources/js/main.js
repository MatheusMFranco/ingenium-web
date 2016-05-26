/* 
	Autor: Matheus Franco
	Criação: 04/2016 
	Arquivo: Main.css
*/

/* Mensagens */
//Erro
var erro = new Array();
erro[0] = "A confirmação de senha deve ser igual à senha informada.";
erro[1] = "A confirmação de e-mail deve ser igual ao e-mail informado.";
erro[2] = "O usuário deve ter pelo menos 3 caracteres.";
erro[3] = "O nome deve ter pelo menos 3 caracteres.";
erro[4] = "E-mail inválido.";
erro[5] = "A senha deve conter mais que 5(cinco) caracteres e menos que 16(dezesseis).";
//Sucesso
var sucesso = new Array();
sucesso[0] = "Cadastro realizado com sucesso!";
/*--*/

$(document).ready( function(){
	var msgSucesso = sucesso[0];
	var msgErro = erro[0];
	$(".msgPOG").hide();
	$(".msgPOG-text").text(msgSucesso);
	
	/* Limpar Campos */
	$(".limpar").click( function (){
		$("input").val(null);
		$("textarea").val(null);
		$("#textoArquivo").text("Selecionar um Arquivo");
		return false;
	});
	/*--*/
	
	/* Voltar Página */
	$(".voltar").click( function voltarPagina(){
		history.back();
	});
	/*--*/
	
	var textoArquivo;
	capturaArquivo();
	
	/* Fechar Mensagem */
	$("#fecharMsg").click( function fecharMensagem(){
		$(this).parent().slideUp();
	});
	/*--*/
});

/* Captura Arquivo */
function capturaArquivo(){
	$("#textoArquivo").text("Selecionar um Arquivo");
	$(".capturaArquivo").change( function pegaArquivo(){
		textoArquivo = $(".capturaArquivo").val();
		$("#textoArquivo").text(textoArquivo);
	});
}
/*--*/

/* Ir para o Topo */
function subirTopo(){
	$("html, body").animate({scrollTop:0}, "slow");
}
/*--*/

function validaEmail(){
	/* Validação de E-mail */
	var email = $(".email").val();
	var confEmail = $(".confirmaEmail").val();
	
	if(email != confEmail){
		$(".email").val(null);
		$(".confirmaEmail").val(null);
		$(".msgPOG").show();
		$(".msgPOG-text").text(erro[1]);
		//$(".email").addClass("ui-state-error");
	}
	/*--*/
	
	if(email.search("@") < 0 || email.search(".") < 0 || email.length < 4){
		$(".email").val(null);
		$(".confirmaEmail").val(null);
		$(".msgPOG").show();
		$(".msgPOG-text").text(erro[4]);
	}
	
	/* Validação de Senha */
	var senha = $(".senha").val();
	var confSenha = $(".confirmaSenha").val();
	
	if(senha != confSenha){
		$(".senha").val(null);
		$(".confirmaSenha").val(null);
		$(".msgPOG").show();
		$(".msgPOG-text").text(erro[0]);
	}
	/*--*/
	
	/* Validação Usuário e Nome */
	var username = $(".username").val();
	var nome = $(".nome").val();

	//Usuário
	if(username.length < 3){
		$(".username").val(null);
		$(".msgPOG").show();
		$(".msgPOG-text").text(erro[2]);
	}
	//Nome
	if(nome.length < 3){
		$(".nome").val(null);
		$(".msgPOG").show();
		$(".msgPOG-text").text(erro[3]);
	}
	
	/* Validação de Sucesso */
	if(email == confEmail && senha == confSenha){
		cadastroRealizado()
	}
	
}

function cadastroUsuario(){ 
	validaEmail();
	subirTopo();
	return true;
}

function cadastroRealizado(){
	if(!args.validationFailed){
		//location.href="../../login.xhtml";
		//localStorage.setItem("sucesso", sucesso[0]);
		$("input").val(null);
		$("textarea").val(null);
		$("#textoArquivo").text("Selecionar um Arquivo");
		$(".msgPOG-text").text(msgSucesso);
		$(".msgPOG").show();
	}
}