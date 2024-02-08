generateNextOrderId(function (nextOrderId) {
    $("#orderId").val(nextOrderId);
});

$("#total").text(0.00);
$("#subTotal").text(0.00);

var date = new Date();
var formattedDate = `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`;
$("#orderDate").val(formattedDate);

loadCustomerIds();
loadItemCodes();
$("#discount").val(0.00);
$("#balance").val(0.00);
$("#btnPlaceOrder").prop("disabled",true);
$("#btnPurchase").prop("disabled",true);
let orderQty;

$("#selectFormCustomer").on('click', function () {
    setCustomerDetails();
    enabledOrDisabledBtn();
    enabledCartBtn()
});

$("#selectItemFormItem").on('click', function () {
    setItemDetails();
    quantityManage();
    enabledOrDisabledBtn()
    enabledCartBtn()
});

$("#btnPlaceOrder").on('click', function () {
    $("#tblPlaceOrder").empty();
    addToPlaceOrderTable();
});

$("#btnPurchase").on('click', function () {
    if (parseFloat($("#cash").val())>= parseFloat($("#subTotal").text())){
        placeOrderDetails();
        clearPlaceOrderTextFields();
        $("#tblPlaceOrder").empty();
        cartDetails = [];
    }else {
        Swal.fire({
            icon: "warning",
            title: "Insufficient credit!",
            text: "Please check cash..."
        });
    }
    loadAllOrderDetails();
});

$("#orderQty").on("keydown keyup", function (e) {
    enabledOrDisabledBtn();
    enabledCartBtn();
});

$("#discount").on("keydown keyup", function (e) {
    calculateSubTotal();
});

$("#cash").on("keydown keyup", function (e) {
    calculateBalance();
    enabledOrDisabledBtn()
});

function generateNextOrderId(callback) {
    $.ajax({
        type : "GET",
        url : "http://localhost:8080/backend/placeOrder?option=generateId",
        success : function (details) {
            console.log("Generate" + details);
            let orderId = details;
            if (orderId != null) {
                let strings = orderId.split("ORD-");
                console.log(orderId);

                let id = parseInt(strings[1]);
                console.log(id);
                ++id;
                let digit = id.toString().padStart(3, "0");
                callback("ORD-"+digit);
            } else {
                callback("ORD-001");
            }
        },
        error: function (error) {
            console.log(error);
            callback("ORD-001");
        }
    });
}

function loadCustomerIds() {
    $("#selectFormCustomer").empty();
    $("#selectFormCustomer").append(`<option selected disabled>Select here</option>`);

    $.ajax({
        type : "GET",
        url : "http://localhost:8080/backend/customers?option=getAll",
        success : function (details) {
            console.log(details);
            console.log(details.custId);
            for (let customer of details) {
                $("#selectFormCustomer").append(`<option>${customer.custId}</option>`);
            }
        },
        error : function (error) {
            console.log(error);
        }
    });
}

function loadItemCodes() {
    $("#selectItemFormItem").empty();
    $("#selectItemFormItem").append(`<option selected disabled>Select here</option>`);
    $.ajax({
        type : "GET",
        url : "http://localhost:8080/backend/items?option=getAll",
        success : function (details) {
            console.log(details);
            console.log(details.itemCode);
            for (let item of details) {
                $("#selectItemFormItem").append(`<option>${item.itemCode}</option>`);
            }
        },
        error : function (error) {
            console.log(error);
        }
    });
}

function setCustomerDetails() {
    $.ajax({
       type : "GET",
       url : "http://localhost:8080/backend/customers?option=search&id=" + $("#selectFormCustomer").val(),
       success : function (details) {
           console.log(details);
           $("#cusId").val(details.custId);
           $("#cusName").val(details.custName);
           $("#cusAddress").val(details.custAddress);
           $("#cusSalary").val(details.custSalary);
       },
       error : function (error) {
           console.log(error);
       }
    });
}

function setItemDetails() {
    $.ajax({
        type : "GET",
        url : "http://localhost:8080/backend/items?option=search&itemCode=" + $("#selectItemFormItem").val(),
        success : function (details) {
            console.log(details);
            $("#itemId").val(details.itemCode);
            $("#itemName").val(details.itemName);
            $("#price").val(details.price);
            $("#quantity").val(details.qty);
        },
        error : function (error) {
            console.log(error);
        }
    });
}

function quantityManage() {
    for (const cartD of cartDetails) {
        if (cartD.itemCode === $("#selectItemFormItem").val()){
            let newQty = $("#quantity").val()-orderQty;
            $("#quantity").val(newQty);
        }
    }
}

function isExists(itemCode) {
    for (const item of cartDetails) {
        if (item.itemCode === itemCode) {
            return item;
        }
    }
    return null;
}

function addToPlaceOrderTable() {
    $("#tblPlaceOrder").empty();
    if (($("#orderQty").val().length!=0) && (parseInt($("#orderQty").val())<=parseInt($("#quantity").val())) ){
        let code = $("#itemId").val();
        let name = $("#itemName").val();
        let price = parseFloat($("#price").val()).toFixed(2);
        orderQty = parseInt($("#orderQty").val())
        let total = (price * orderQty).toFixed(2);
        let exists = isExists($("#selectItemFormItem").val());

        if (exists != null) {
            exists.quantity = exists.quantity + orderQty;
            total = (price * exists.quantity).toFixed(2);
            exists.total = total;
        } else {
            var cartTm = Object.assign({}, cartModel);
            cartTm.itemCode = code;
            cartTm.itemName = name;
            cartTm.unitPrice = price;
            cartTm.quantity = orderQty;
            cartTm.total = total;
            cartDetails.push(cartTm);
        }
        Swal.fire({
            position: "center",
            icon : "success",
            title : "Order has been added to the cart successfully!...",
            showConfirmButton: false,
            timer: 2000
        });
        quantityManage();
        calculateTotal();
        $("#orderQty").val("");
        loadAllOrderDetails();
    }else {
        Swal.fire({
            icon: "warning",
            title: "Please check the quantity!.",
            text: "Order has not been added to the cart!."
        });
        loadAllOrderDetails();
    }
}

function getItemDetails() {
    let rows = $("#tblPlaceOrder").children().length;
    console.log(rows);
    var array = [];
    for (let i = 0; i < rows; i++) {
        let itemCode = $("#tblPlaceOrder").children().eq(i).children(":eq(0)").text();
        let itemQty = $("#tblPlaceOrder").children().eq(i).children(":eq(3)").text();
        let itemPrice = $("#tblPlaceOrder").children().eq(i).children(":eq(4)").text();
        console.log(itemCode,itemQty, itemPrice);
        array.push({itemCode: itemCode, qty: itemQty, unitPrice: itemPrice});
    }
    return array;
}

function placeOrderDetails() {
    let orderId = $("#orderId").val();
    let orderDate = $("#orderDate").val();
    let customerId = $("#cusId").val();
    let orderDetails = getItemDetails();
    console.log(orderId);

    let orderObj = {
        orderId : orderId,
        orderDate : orderDate,
        custId : customerId,
        orderDetails : orderDetails
    };

    $.ajax({
        type : "POST",
        url : "http://localhost:8080/backend/placeOrder",
        contentType : "application/json",
        data : JSON.stringify(orderObj),
        success : function (details) {
            console.log(details);
            Swal.fire({
                icon : "success",
                title : "Order has been placed successfully!...",
                showConfirmButton: false,
                timer: 2000
            });
            generateNextOrderId(function (nextOrderId) {
                $("#orderId").val(nextOrderId);
            });
            loadAllOrderDetails()
        },
        error : function (jqXHR, textStatus, errorThrown) {
            console.log("AJAX error: " + textStatus, errorThrown, jqXHR);
            Swal.fire({
                position: "top-up",
                icon: "warning",
                title: "Order has been placed unsuccessfully!!!",
                timer: 2000
            });
        }
    });
}

function loadAllOrderDetails() {
    for (let i = 0; i < cartDetails.length; i++) {
        let row = `<tr>
                     <td>${cartDetails[i].itemCode}</td>
                     <td>${cartDetails[i].itemName}</td>
                     <td>${cartDetails[i].unitPrice}</td>
                     <td>${cartDetails[i].quantity}</td>
                     <td>${cartDetails[i].total}</td>
                    </tr>`;
        $("#tblPlaceOrder").append(row);
        doubleClickItem();
    }
}

// function bindTableRowEventsOrder() {
//     $("#tblPlaceOrder>tr").click(function (){
//         let itemCode = $(this).children().eq(0).text();
//         let name = $(this).children().eq(1).text();
//         let price = $(this).children().eq(2).text();
//         let quantity = $(this).children().eq(3).text();
//         let total = $(this).children().eq(4).text()
//
//         $("#itemId").val(itemCode);
//         $("#itemName").val(name);
//         $("#price").val(price);
//         $("#quantity").val(quantity);
//         $("#total").val(total);
//     });
// }

function calculateTotal() {
    let Total =0;
    for (let i = 0; i <cartDetails.length; i++) {
        Total=Total+parseFloat(cartDetails[i].total)
    }
    $("#total, #subTotal").text(Total.toFixed(2));
}

function calculateSubTotal() {
    let subTotal = parseFloat($("#total").text());
    console.log(subTotal);
    let discount = subTotal * parseFloat($("#discount").val()) * 0.01;
    console.log(discount);
    let newSubTotal = subTotal - discount;
    console.log(newSubTotal);
    $("#subTotal").text(newSubTotal.toFixed(2));
}

function calculateBalance() {
    if ($("#cash").val().length!=0 && ($("#subTotal").text().length!=0)){
        let subTotal = parseFloat($("#subTotal").text());
        let cash = parseFloat($("#cash").val());
        console.log(cash);
        let balance = cash - subTotal;
        $("#balance").val(balance.toFixed(2));
    }else {
        $("#balance").val(0.00);
    }
}

doubleClickItem();
function doubleClickItem() {
    $("#tblPlaceOrder>tr").dblclick(function () {
        // alert("hi");
        let confirmation = confirm("Are you want to delete this order?");
        if (confirmation){
            $(this).remove();
            alert("Order removed!");
            clearPlaceOrderTextFields();
        }else{
            alert("Order not removed")
        }
    });
}

