getAllCustomer();

// --------------Search customer function---------------------------
$("#btnSearchCustomer").click(function () {
    let cusId = $("#exampleInput").val();
    $("#tblCustomer").empty();
    $.ajax({
        type : "GET",
        url: "http://localhost:8080/backend/customers?option=search&id=" + cusId,
        success : function (details) {
            console.log(details);
            console.log(details.custId);

            let row =  `<tr>
                     <td>${details.custId}</td>
                     <td>${details.custName}</td>
                     <td>${details.custAddress}</td>
                     <td>${details.custSalary}</td>
                    </tr>`;

            $("#tblCustomer").append(row);
            bindTableRowEventsCustomer();
        },
        error : function (error) {
            Swal.fire({
                icon: "warning",
                title: "Oooops...",
                text: "No result found!..."
            })
        }
    });
});

// --------------Search customer function---------------------------
function searchCustomer(customerId) {
    $.ajax({
        type : "GET",
        url: "http://localhost:8080/backend/customers?option=search&id=" + customerId,
        success : function (details) {
            console.log(details);
            return true;
        },
        error : function (error) {
            console.log(error);
            Swal.fire({
                icon: 'warning',
                title: 'Oops...',
                text: '"No such Customer..please check the ID..!',
            })
            return false;
        }
    });
}

// --------------Save btn event---------------------------
$("#btnSaveCustomer").click(function (){
    if (checkAllCustomer()){
        saveCustomer();
    }else {
        alert("Something went wrong!");
    }
});

// --------------Save Customer function---------------------------
function saveCustomer() {
    let cusId = $("#txtCusId").val();
    let cusName = $("#txtCusName").val();
    let cusAddress = $("#txtCusAddress").val();
    let cusSalary = $("#txtCusSalary").val();

    $.ajax({
        type : "POST",
        url : "http://localhost:8080/backend/customers",
        contentType : "application/json",
        data : JSON.stringify({
                custId : cusId,
                custName : cusName,
                custAddress : cusAddress,
                custSalary : cusSalary
            }),

        success : function (details) {
            console.log(details);
            Swal.fire({
                position: "center",
                icon : "success",
                title : "Customer has been saved successfully!...",
                showConfirmButton: false,
                timer: 2000
            });
            clearCustomerInputFields();
            getAllCustomer();
            loadCustomerIds();
        },
        error : function (jqXHR, textStatus, errorThrown) {
            console.log("AJAX Error: " + textStatus, errorThrown, jqXHR);
            if (jqXHR.status == 409) {
                Swal.fire({
                    position: "center",
                    icon: "warning",
                    title: jqXHR.responseText,
                    showConfirmButton: false,
                    timer: 2000
                });
            }
        }
    });
}

// --------------Get all btn event---------------------------
$("#getAll").click(function (){
    getAllCustomer();
})

// --------------Get all customer function---------------------------
function getAllCustomer(){
    $("#tblCustomer").empty();
    $("#modalTable").empty();

    $.ajax({
       type: "GET",
       url: "http://localhost:8080/backend/customers?option=getAll",
        success : function (details) {
           console.log("Success: ", details);
           console.log(details.custId);
           for (let customer of details) {

               let row = `<tr>
                        <td>${customer.custId}</td>
                        <td>${customer.custName}</td>
                        <td>${customer.custAddress}</td>
                        <td>${customer.custSalary}</td>
                        </tr>`;

               $("#tblCustomer").append(row);
               $("#modalTable").append(row);
               bindTableRowEventsCustomer();
           }
        },
        error: function (error) {
           console.log(error);
            Swal.fire({
                icon: "warning",
                title: "Oooops...",
                text: "No result found!..."
            });
        }
    });
}

// --------------Bind row to fields function---------------------------
function bindTableRowEventsCustomer() {
    $("#tblCustomer>tr").click(function (){
        let id = $(this).children().eq(0).text();
        let name = $(this).children().eq(1).text();
        let address = $(this).children().eq(2).text();
        let salary = $(this).children().eq(3).text();

        $("#txtCusId").val(id);
        $("#txtCusName").val(name);
        $("#txtCusAddress").val(address);
        $("#txtCusSalary").val(salary);

        $("#btnDeleteCustomer").prop("disabled", false);
    });
}

// --------------Delete btn event---------------------------
$("#btnDeleteCustomer").click(function (){
    let id = $("#txtCusId").val();
    deleteCustomer(id);
});

// --------------Delete customer function---------------------------
function deleteCustomer(id) {
    Swal.fire({
        position: "center",
        icon : "warning",
        title : "Do you want to delete this customer ?.",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes",
        cancelButtonText: "No"
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                type : "DELETE",
                url : "http://localhost:8080/backend/customers?id="+id,
                contentType : "application/json",
                success : function (details) {
                    Swal.fire({
                        position: "top-up",
                        icon : "success",
                        title : "Customer has been deleted successfully!...",
                        showConfirmButton: false,
                        timer: 2000
                    });
                    clearCustomerInputFields();
                    getAllCustomer();
                },
                error : function (jqXHR, textStatus, errorThrown) {
                    console.log("ÄJAX error: "+ textStatus, errorThrown);
                    Swal.fire({
                        position: "top-up",
                        icon: "warning",
                        title: "Customer has been deleted unsuccessfully!!!",
                        timer: 2000
                    });
                }
            });
        }
    });
    return false;
}

// --------------Update btn event---------------------------
$("#btnUpdateCustomer").click(function (){
   let id = $("#txtCusId").val();
   updateCustomer(id);
   clearCustomerInputFields();
});

// --------------Update Customer function---------------------------
function updateCustomer(id) {
    if (searchCustomer(id)) {
        Swal.fire({
            icon: "warning",
            title: "Oooops...",
            text: "No such customer. Please check the ID!..."
        });
    } else {
        let confirmation = confirm("Do you want to update this customer ?.")
            // Swal.fire({
            //     position: "center",
            //     icon : "warning",
            //     title : "Do you want to update this customer ?.",
            //     showConfirmButton: true,
            //     timer: 2000
            // });

        if (confirmation) {
            console.log(id);
            let cusId = $("#txtCusId").val();
            let cusName = $("#txtCusName").val();
            let cusAddress = $("#txtCusAddress").val();
            let cusSalary = $("#txtCusSalary").val();

            $.ajax({
                type : "PUT",
                url : "http://localhost:8080/backend/customers",
                contentType: "application/json",
                data : JSON.stringify(
                    {
                        custId : cusId,
                        custName : cusName,
                        custAddress : cusAddress,
                        custSalary : cusSalary
                    }),
                success : function (details) {
                    console.log(details);
                    Swal.fire({
                        position: "center",
                        icon : "success",
                        title : "Customer has been updated successfully!...",
                        showConfirmButton: false,
                        timer: 2000
                    });
                    clearCustomerInputFields();
                    getAllCustomer();
                },
                error : function (jqXHR, textStatus, errorThrown) {
                    console.log("AJAX Error: " + textStatus, errorThrown, jqXHR);
                    Swal.fire({
                        position: "center",
                        icon: "warning",
                        title: "Customer has been not updated!!!",
                        showConfirmButton: false,
                        timer: 2000
                    });
                }
            });
        }
    }
}

// --------------Clear btn event---------------------------
$("#btnClearCustomer").click(function (){
    clearCustomerInputFields();
});