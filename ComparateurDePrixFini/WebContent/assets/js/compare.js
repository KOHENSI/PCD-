/**
 * 
 */

function updateBulle(nbrElements){
	var bulle = document.getElementById("bulle");
	if ( bulle == null ) return ;
	else
		{
			if ( nbrElements == 0 )
				bulle.innerHTML="";
			else
			{
				bulle.innerHTML="<div class='basket-item-count'><span class='count'>"+nbrElements+"</span></div>";
			}
		}
}

function changeButtonState(id){
	
	var cmpBtn = document.getElementById(id);
	if ( cmpBtn == null ) return;
	if (cmpBtn.classList.contains("activated"))
	{
		cmpBtn.classList.remove("activated");
	}else
	{
		cmpBtn.classList.add("activated");
	}
}
	
function buttonCompare(id) {
	
	changeButtonState(id);
	   var url = "compareb?id=" + id;
	   if (window.XMLHttpRequest) {
	       requete = new XMLHttpRequest();
	   } else if (window.ActiveXObject) {
	       requete = new ActiveXObject("Microsoft.XMLHTTP");
	   }
	   requete.open("GET", url, true);
	   requete.onreadystatechange = showProduts;
	   requete.send(null);
}
	
function showProduts( ){
	var message = "";
	if (requete.readyState == 4) {
	    if (requete.status == 200) {
	      // exploitation des données de la réponse
	      var products = requete.responseXML.getElementsByTagName("product");
	      for (i = 0 ; i<products.length;i++)
	    	{
	    	  	var name= products[i].childNodes[0].childNodes[0];
	    	  	var nametxt = name.nodeValue;
	    	  	var link= products[i].childNodes[1].childNodes[0];
	    	  	var linktxt = link.nodeValue;
	    	  	var id= products[i].childNodes[2].childNodes[0];
	    	  	var idtxt = id.nodeValue;
	    	  	//alert(nametxt + " " + id);
	    	  	message+=
	    	  	'<div class="cart-item product-summary">'
				+'<div class="row">'
				+'	<div class="col-xs-4">'
				+'		<div class="image">'
				+'			<a href="detail.html"><img src="'+linktxt+'" alt=""></a>'
				+'		</div>'
				+'	</div>'
				+'	<div class="col-xs-7">'
				+''		
				+'		<h3 class="name"><a href="index.php?page-detail">'+nametxt+'</a></h3>'
				+''
				+'	</div>'
				+'	<div class="col-xs-1 action">'
				+'		<a onclick="buttonCompare('+idtxt+');"><i class="fa fa-trash" ></i></a>'
				+'	</div>'
				+'</div>'
			    +'</div><!-- /.cart-item -->';
	    	    };
	    updateBulle(products.length);
	    if (message == "") message = "Auncun Produit Séléctioné"; 
	      document.getElementById("prodcomp").innerHTML=message;
	    
	    }
	}
}