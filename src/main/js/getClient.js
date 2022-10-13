const clientForm = document.getElementById('ClientForm');
clientForm.addEventListener("mousemove", getClient);

function getClient(event) {
    event.preventDefault();
    const nameOutput = document.getElementById('name');
    const birthDateOutput = document.getElementById('birthDate');
    const emailOutput = document.getElementById('email');

    const response = fetch('http://localhost:8080/client/getClientById?clientId=1', {
        method: 'GET',
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
//            'Authorization': "Basic " + btoa("user" + ":" + "123456")
        },
    })
        .then((response) => response.json())
        .then((json) => console.log(json));

    nameOutput.value = response.
}