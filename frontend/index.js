const fetchData = () => {
    fetch("http://localhost:8080/addresses")
        .then((response) => response.json())
        .then((data) => {
            createEntry(data)
        })
}
document.addEventListener("DOMContentLoaded", () => {
    fetchData();
});


const createEntry = (data) => {
    const wrapper = document.getElementById("addresses_wrapper");
    wrapper.innerHTML = '';
    for (let i = 0; i < data.length; i++) {
        const contact = document.createElement("div");
        contact.classList.add("address_entry");
        contact.setAttribute("id", data[i].id);

        const name = document.createElement("div");
        name.innerText = `${data[i].name} ${data[i].surname}`;
        name.classList.add("address_name");

        const address = document.createElement("div");
        address.innerText = `${data[i].street}, ${data[i].city} ${data[i].postCode}`;
        name.classList.add("address_name");

        const buttonEdit = createButton("button_edit", "Edit");
        buttonEdit.addEventListener("click", (event) => {
            const target = event.target;
            const parent = target.parentNode;
            const id = parent.getAttribute("id");
            fetchById(id);
        })

        const buttonDelete = createButton("button_delete", "Delete");
        buttonDelete.addEventListener("click", (event) => {
            const target = event.target;
            const parent = target.parentNode;
            const id = parent.getAttribute("id");
            wrapper.removeChild(parent);
            deleteData(id);
        })
        contact.append(name, address, buttonEdit, buttonDelete);
        wrapper.append(contact);
    }
}

const createButton = (className, content) => {
    const button = document.createElement("button");
    button.classList.add(className);
    button.innerText = content;
    return button;
}

const deleteData = (id) => {
    fetch(`http://localhost:8080/addresses/${id}`, {
        method: 'DELETE',

    })
        .catch((error) => console.log(error))
}

const createNewEntry = (event) => {
    event.preventDefault();
    const name = document.getElementById("fname");
    const surname = document.getElementById("lname");
    const street = document.getElementById("street");
    const city = document.getElementById("city");
    const postCode = document.getElementById("pcode");
    const body = {
        "id": `${Math.floor(Math.random() * 100)}`,
        "street": `${street.value}`,
        "city": `${city.value}`,
        "postCode": `${postCode.value}`,
        "name": `${name.value}`,
        "surname": `${surname.value}`,
    }
    addData(body);
    hideForm();
}

const showForm = () => {
    const showForm = document.querySelector(".address_form");
    showForm.classList.add("show");
}
const hideForm = () => {
    const showForm = document.querySelector(".address_form");
    showForm.classList.remove("show");
}

const addData = (body) => {
    fetch(`http://localhost:8080/addresses`, {
        method: 'POST',
        body: JSON.stringify(body),
        headers: { 'Content-Type': 'application/json', 'charset': 'utf-8' }
    })
        .finally(() => {
            const main = document.querySelector("main");
            fetchData(main);
        })
}

const searchByName = () => {
    const nameValue = document.getElementById("searchName").value;
    fetch(`http://localhost:8080/addresses/name/${nameValue}`)
        .then((response) => response.json())
        .then((data) => {
            if(data.length > 0){
                createEntry([...data]);
            }
            else{
                const wrapper = document.querySelector("#addresses_wrapper");
                wrapper.innerHTML = '';
                const errorMessage = document.querySelector(".error")
                errorMessage.classList.add("show");
            }
        })

}

const searchByStreet = () => {
    const streetValue = document.getElementById("searchStreet").value;
    fetch(`http://localhost:8080/addresses/street/${streetValue}`)
        .then((response) => response.json())
        .then((data) => {
            if(data.length > 0){
                createEntry([...data]);
            }
            else{
                const wrapper = document.querySelector("#addresses_wrapper");
                wrapper.innerHTML = '';
                const errorMessage = document.querySelector(".error")
                errorMessage.classList.add("show");
            }
        })
}

const fetchById = (id) => {
    fetch(`http://localhost:8080/addresses/${id}`)
        .then((response) => response.json())
        .then((data) => {
            createEditForm(data,id);
        })
}

const createEditForm = (data,id) => {
    const editForm = document.querySelector(".address_form_edit");
    editForm.setAttribute("id", id);
    editForm.classList.add("show");
    document.getElementById("fname_edit").value = data.name;
    document.getElementById("lname_edit").value = data.surname;
    document.getElementById("street_edit").value = data.street;
    document.getElementById("city_edit").value = data.city;
    document.getElementById("pcode_edit").value = data.postCode;
}

const updateEntry = (event) => {
    event.preventDefault();
    const editForm = document.querySelector(".address_form_edit");
    const id = editForm.getAttribute("id");
    const name = document.getElementById("fname_edit");
    const surname = document.getElementById("lname_edit");
    const street = document.getElementById("street_edit");
    const city = document.getElementById("city_edit");
    const postCode = document.getElementById("pcode_edit");
    const body = {
        "id": `${id}`,
        "street": `${street.value}`,
        "city": `${city.value}`,
        "postCode": `${postCode.value}`,
        "name": `${name.value}`,
        "surname": `${surname.value}`,
    }
    console.log(body, id);
    putEntry(body, id);
}

const putEntry = (body, id) => {
    fetch(`http://localhost:8080/addresses/${id}`, {
        method: 'PUT',
        body: JSON.stringify(body),
        headers: { 'Content-Type': 'application/json', 'charset': 'utf-8' }
    })
        .finally(() => {
            const main = document.querySelector("main");
            fetchData(main);
            const editForm = document.querySelector(".address_form_edit");
            editForm.classList.remove("show")
        })
}

const reset= () =>{
    document.getElementById("searchStreet").value = '';
    document.getElementById("searchName").value = '';
    const errorMessage = document.querySelector(".error")
    errorMessage.classList.remove("show");
    fetchData();
}