// getAllItem();
//
// // --------------Save btn event---------------------------
// $("#btnSaveItem").click(function (){
//     if (checkAllItems()){
//         saveItem();
//     }else {
//         alert("Something went wrong!");
//     }
// });
//
// // --------------Get all btn event---------------------------
// $("#btnGetAllItem").click(function (){
//     getAllItem();
// })
//
// // --------------Search customer function---------------------------
// function searchItem(itemCode) {
//     return itemsDB.find(function (item){
//         return item.code == itemCode;
//     });
// }
//
// // --------------Save Customer function---------------------------
// function saveItem() {
//     let itemCode = $("#txtItemCode").val();
//     // check customer if exists or not
//     if(searchItem(itemCode.trim()) == undefined){
//         let itemName = $("#txtItemName").val();
//         let itemPrice = $("#txtItemPrice").val();
//         let itemQuantity = $("#txtItemQuantity").val();
//
//         let newItem = Object.assign({}, itemModel);
//
//         newItem.code = itemCode;
//         newItem.name = itemName;
//         newItem.price = itemPrice;
//         newItem.quantity = itemQuantity;
//
//         itemsDB.push(newItem);
//         clearItemInputFields();
//         getAllItem();
//         alert("Item added!");
//         loadItemCodes();
//     }else{
//         alert("Item exists!")
//         clearItemInputFields();
//     }
// }
//
// // --------------Get all customer function---------------------------
// function getAllItem(){
//     $("#tblItem").empty();
//     $("#modalItemTable").empty();
//
//     for (let i=0; i<itemsDB.length; i++){
//         let id = itemsDB[i].code;
//         let name = itemsDB[i].name;
//         let price = itemsDB[i].price;
//         let quantity = itemsDB[i].quantity;
//
//         let row =  `<tr>
//                         <td>${id}</td>
//                         <td>${name}</td>
//                         <td>${price}</td>
//                         <td>${quantity}</td>
//                     </tr>`;
//
//         $("#tblItem").append(row);
//         $("#modalItemTable").append(row);
//
//         bindTableRowEventsItem();
//     }
// }
//
// // --------------Bind row to fields function---------------------------
// function bindTableRowEventsItem() {
//     $("#tblItem>tr").click(function (){
//         let code = $(this).children().eq(0).text();
//         let name = $(this).children().eq(1).text();
//         let price = $(this).children().eq(2).text();
//         let quantity = $(this).children().eq(3).text();
//
//         $("#txtItemCode").val(code);
//         $("#txtItemName").val(name);
//         $("#txtItemPrice").val(price);
//         $("#txtItemQuantity").val(quantity);
//
//         $("#btnDeleteItem").prop("disabled", false);
//     });
// }
//
// // --------------Delete btn event---------------------------
// $("#btnDeleteItem").click(function (){
//     let code = $("#txtItemCode").val();
//
//     let confirmation = confirm("Are you want to delete "+code+" ?");
//     if (confirmation){
//         let response = deleteItem(code);
//         if (response){
//             clearItemInputFields();
//             getAllItem();
//             alert("Item deleted!.");
//         }else {
//             alert("Item not deleted!.")
//         }
//     }
// });
//
// // --------------Delete customer function---------------------------
// function deleteItem(code) {
//     for (let i = 0; i < itemsDB.length; i++){
//         if (itemsDB[i].code == code){
//             itemsDB.splice(i,1);
//             return true;
//         }
//     }
//     return false;
// }
//
// // --------------Update btn event---------------------------
// $("#btnUpdateItem").click(function (){
//     let code = $("#txtItemCode").val();
//     updateItem(code);
//     clearItemInputFields();
// });
//
// // --------------Update Customer function---------------------------
// function updateItem(code) {
//     if(searchItem(code) == undefined){
//         alert("No such item. Please check the ID!");
//     }else{
//         let confirmation = confirm("Do you really want to update "+code+".?");
//         if (confirmation){
//             let item = searchItem(code);
//
//             let name = $("#txtItemName").val();
//             let price = $("#txtItemPrice").val();
//             let quantity = $("#txtItemQuantity").val();
//
//             item.name = name;
//             item.price = price;
//             item.quantity = quantity;
//
//             getAllItem();
//             alert("Item updated!");
//         }
//     }
// }
//
// // --------------Clear btn event---------------------------
// $("#btnClearItem").click(function (){
//     clearItemInputFields();
// });
//
//
//
//

getAllItem();

// --------------Search customer function---------------------------
$("#btnItemSearch").click(function () {
    let code = $("#txtItemSearch").val();
    $("#tblItem").empty();
    $.ajax({
        type : "GET",
        url: "http://localhost:8080/backend/items?option=search&itemCode=" + code,
        success : function (details) {
            console.log(details);
            console.log(details.itemCode);

            let row =  `<tr>
                     <td>${details.itemCode}</td>
                     <td>${details.itemName}</td>
                     <td>${details.price}</td>
                     <td>${details.qty}</td>
                    </tr>`;

            $("#tblItem").append(row);
            bindTableRowEventsItem();
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
function searchItem(itemCode) {
    $.ajax({
        type : "GET",
        url: "http://localhost:8080/backend/customers?option=search&itemCode=" + itemCode,
        success : function (details) {
            console.log(details);
            return true;
        },
        error : function (error) {
            console.log(error);
            Swal.fire({
                icon: 'warning',
                title: 'Oops...',
                text: '"No such item..please check the ID..!',
            })
            return false;
        }
    });
}

// --------------Save btn event---------------------------
$("#btnSaveItem").click(function (){
    if (checkAllItems()){
        saveItem();
    }else {
        alert("Something went wrong!");
    }
});

// --------------Save Customer function---------------------------
function saveItem() {
    let itemCode = $("#txtItemCode").val();
    let itemName = $("#txtItemName").val();
    let price = $("#txtItemPrice").val();
    let qty = $("#txtItemQuantity").val();

    $.ajax({
        type : "POST",
        url : "http://localhost:8080/backend/items",
        contentType : "application/json",
        data : JSON.stringify({
            itemCode : itemCode,
            itemName : itemName,
            price : price,
            qty : qty
        }),

        success : function (details) {
            console.log(details);
            Swal.fire({
                position: "center",
                icon : "success",
                title : "Item has been saved successfully!...",
                showConfirmButton: false,
                timer: 2000
            });
            clearItemInputFields();
            getAllItem();
            loadItemCodes();
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
$("#btnGetAllItem").click(function (){
    getAllItem();
})

// --------------Get all customer function---------------------------
function getAllItem(){
    $("#tblItem").empty();
    $("#modalItemTable").empty();

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/backend/items?option=getAll",
        success : function (details) {
            console.log("Success: ", details);
            console.log(details.itemCode);
            for (let item of details) {

                let row = `<tr>
                        <td>${item.itemCode}</td>
                        <td>${item.itemName}</td>
                        <td>${item.price}</td>
                        <td>${item.qty}</td>
                        </tr>`;

                $("#tblItem").append(row);
                $("#modalItemTable").append(row);
                bindTableRowEventsItem();
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
function bindTableRowEventsItem() {
    $("#tblItem>tr").click(function (){
        let code = $(this).children().eq(0).text();
        let name = $(this).children().eq(1).text();
        let price = $(this).children().eq(2).text();
        let quantity = $(this).children().eq(3).text();

        $("#txtItemCode").val(code);
        $("#txtItemName").val(name);
        $("#txtItemPrice").val(price);
        $("#txtItemQuantity").val(quantity);

        $("#btnDeleteItem").prop("disabled", false);
    });
}

// --------------Delete btn event---------------------------
$("#btnDeleteItem").click(function (){
    let code = $("#txtItemCode").val();
    deleteItem(code);
});

// --------------Delete customer function---------------------------
function deleteItem(code) {
    Swal.fire({
        position: "center",
        icon : "warning",
        title : "Do you want to delete this item ?.",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes",
        cancelButtonText: "No"
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                type : "DELETE",
                url : "http://localhost:8080/backend/items?itemCode="+code,
                contentType : "application/json",
                success : function (details) {
                    Swal.fire({
                        position: "top-up",
                        icon : "success",
                        title : "Item has been deleted successfully!...",
                        showConfirmButton: false,
                        timer: 2000
                    });
                    clearItemInputFields();
                    getAllItem();
                },
                error : function (jqXHR, textStatus, errorThrown) {
                    console.log("ÄJAX error: "+ textStatus, errorThrown);
                    Swal.fire({
                        position: "top-up",
                        icon: "warning",
                        title: "Item has been deleted unsuccessfully!!!",
                        timer: 2000
                    });
                }
            });
        }
    });
    return false;
}

// --------------Update btn event---------------------------
$("#btnUpdateItem").click(function (){
    let code = $("#txtItemCode").val();
    updateItem(code);
    clearItemInputFields();
});

// --------------Update Customer function---------------------------
function updateItem(code) {
    if (searchItem(code)) {
        Swal.fire({
            icon: "warning",
            title: "Oooops...",
            text: "No such item. Please check the ID!..."
        });
    } else {
        let confirmation = confirm("Do you want to update this item ?.")
        // Swal.fire({
        //     position: "center",
        //     icon : "warning",
        //     title : "Do you want to update this customer ?.",
        //     showConfirmButton: true,
        //     timer: 2000
        // });

        if (confirmation) {
            console.log(code);
            let itemCode = $("#txtItemCode").val();
            let itemName = $("#txtItemName").val();
            let price = $("#txtItemPrice").val();
            let qty = $("#txtItemQuantity").val();

            $.ajax({
                type : "PUT",
                url : "http://localhost:8080/backend/items",
                contentType: "application/json",
                data : JSON.stringify(
                    {
                        itemCode : itemCode,
                        itemName : itemName,
                        price : price,
                        qty : qty
                    }),
                success : function (details) {
                    console.log(details);
                    Swal.fire({
                        position: "center",
                        icon : "success",
                        title : "Item has been updated successfully!...",
                        showConfirmButton: false,
                        timer: 2000
                    });
                    clearItemInputFields();
                    getAllItem();
                },
                error : function (jqXHR, textStatus, errorThrown) {
                    console.log("AJAX Error: " + textStatus, errorThrown, jqXHR);
                    Swal.fire({
                        position: "center",
                        icon: "warning",
                        title: "Item has been not updated!!!",
                        showConfirmButton: false,
                        timer: 2000
                    });
                }
            });
        }
    }
}

// --------------Clear btn event---------------------------
$("#btnClearItem").click(function (){
    clearItemInputFields();
});