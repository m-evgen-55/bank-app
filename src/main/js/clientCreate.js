const clientCreateForm = document.getElementById('ClientCreateForm');
clientCreateForm.addEventListener("submit", createClient);

function createClient(event) {
    event.preventDefault();
    const nameInput = document.getElementById('name');
    const birthDateInput = document.getElementById('birthDate');
    const emailInput = document.getElementById('email');

    fetch('http://localhost:8080/client/addNewClient', {
        method: 'POST',
        body: JSON.stringify({
            name: nameInput.value,
            birthDate: birthDateInput.value,
            email: emailInput.value
        }),
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
//            'Authorization': "Basic " + btoa("user" + ":" + "123456")
        },
    })
        .then((response) => response.json())
        .then((json) => console.log(json));
}