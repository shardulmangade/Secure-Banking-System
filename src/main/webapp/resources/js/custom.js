function changeContent(id, url) {
    //$('#' + id).load(url);
    $.post( url, function( data ) {
  	  	$( "#"+ id ).html( data );
  	});
}

