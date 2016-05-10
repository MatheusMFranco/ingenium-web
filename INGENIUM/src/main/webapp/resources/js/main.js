/* 
	Autor: Matheus Franco
	Criação: 04/2016 
	Arquivo: Main.css
*/

$(document).ready( function(){
	/* Limpar Campos */
	$(".limpar").click( function (){
		$("input").val(null);
		return false;
	});
	/*--*/
	var textoArquivo;
	capturaArquivo();
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
