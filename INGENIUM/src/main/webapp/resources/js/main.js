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
		$(".msgPOG").show();
		$(".msgPOG-text").text(erro[1]);
		$(".email").val(null);
		//$(".email").addClass("ui-state-error");
	}
	/*--*/
	
	/* Validação de E-mail */
	var senha = $(".senha").val();
	var confSenha = $(".confirmaSenha").val();
	
	if(senha != confSenha){
		$(".msgPOG").show();
		$(".msgPOG-text").text(erro[0]);
		$(".senha").val(null);
	}
	/*--*/
	
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
		location.href="../../login.xhtml";
		localStorage.setItem("sucesso", sucesso[0]);
		$(".msgPOG-text").text(msgSucesso);
		$(".msgPOG").show();
	}
}