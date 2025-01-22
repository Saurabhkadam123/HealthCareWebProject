

function onBodyLoad() {
	
						/*Dynamic info*/
	var selectedRadio = PF('mySelectName').getJQ().find(':checked').val();
	
	
	var idSearchBoxDiv = document.getElementById('text1');

	var textlabel = document.getElementById('myForm:text');

	var paid = document.getElementById('myForm:paid');
	var dropdown_paid = document.getElementById('myForm:dropdown_paid');

	var status = document.getElementById('myForm:status');
	var dropdown_status = document.getElementById('myForm:dropdown_status');

	var searchButton = document.getElementById('myForm:searchButton');

	var table = document.getElementById('myForm:allData');
	
	
					/*Error Msg*/
	var text_Msg = document.getElementById('myForm:textMsg');
	var paid_Msg = document.getElementById('myForm:paidMsg');
	var status_Msg = document.getElementById('myForm:statusMsg');
	
	
	

	if (selectedRadio == "PaymentID" || selectedRadio == "ProcedureID"
			|| selectedRadio == "HealthID") {

		textlabel.innerHTML = selectedRadio + " : "
		idSearchBoxDiv.style.display = 'block';

		paid.style.display = 'none';
		dropdown_paid.style.display = 'none';

		status.style.display = 'none';
		dropdown_status.style.display = 'none';

		searchButton.style.display = 'block';
		table.style.display = 'none';
		
		text_Msg.style.display = 'none';
		paid_Msg.style.display = 'none';
		status_Msg.style.display = 'none';
		
		

	} else if (selectedRadio == "paidThrough") {

		textlabel.style.display = 'none';
		idSearchBoxDiv.style.display = 'none';

		paid.style.display = 'block';
		dropdown_paid.style.display = 'block';

		status.style.display = 'none';
		dropdown_status.style.display = 'none';

		searchButton.style.display = 'block';
		table.style.display = 'none';
		
		text_Msg.style.display = 'none';
		paid_Msg.style.display = 'none';
		status_Msg.style.display = 'none';
		

	} else if (selectedRadio == "paymentsStatus") {

		textlabel.style.display = 'none';
		idSearchBoxDiv.style.display = 'none';
		paid.style.display = 'none';
		dropdown_paid.style.display = 'none';

		status.style.display = 'block';
		dropdown_status.style.display = 'block';

		searchButton.style.display = 'block';
		table.style.display = 'none';
		
		text_Msg.style.display = 'none';
		paid_Msg.style.display = 'none';
		status_Msg.style.display = 'none';

	} else {
		idSearchBoxDiv.style.display = 'none';
		paid.style.display = 'none';
		dropdown_paid.style.display = 'none';

		status.style.display = 'none';
		dropdown_status.style.display = 'none';

		searchButton.style.display = 'none';
		table.style.display = 'none';
		
		text_Msg.style.display = 'none';
		paid_Msg.style.display = 'none';
		status_Msg.style.display = 'none';
		

	}
}



function showTable() {
	var tableStruct = document.getElementById('myForm:allData');
	tableStruct.style.display = 'block';
}

function empty() {
	var textfield = document.getElementById('myForm:textfield');
	var dropdown_paid = document.getElementById('myForm:dropdown_paid');
	var dropdown_status = document.getElementById('myForm:dropdown_status');
	var radios = document.getElementById('myForm:radios');
	textfield.value = "";
	
	
}

function showDetails() {
	var myval = PF('mySelectName').getJQ().find(':checked').val();
	myval = "";
}

function msg(){
	var text_Msg = document.getElementById('myForm:textMsg');
	var paid_Msg = document.getElementById('myForm:paidMsg');
	var status_Msg = document.getElementById('myForm:statusMsg');
	
	text_Msg.style.display = 'block';
	
	var selectedRadio = PF('mySelectName').getJQ().find(':checked').val();
	
	if (selectedRadio == "paidThrough") {
		
		paid_Msg.style.display = 'block';
		status_Msg.style.display = 'none';
		
	}else if (selectedRadio == "paymentsStatus"){
		
		paid_Msg.style.display = 'none';
		status_Msg.style.display = 'block';
		
	}
	
}








