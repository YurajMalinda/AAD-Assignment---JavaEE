loadOrderDetails();
$("#txtOrderDetailSearch").focus();
$("#btnGetAllOrderDetail").on("click", function () {
    loadOrderDetails()
});

$("#btnSearchOrderDetail").on("click", function () {
    searchOrderDetail();
});

function loadOrderDetails() {
    $('#tblOrderDetails').empty();
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/backend/orderDetails?option=getAll",
        success: function (details) {
            console.log(details);
            for (let orderDetails of details) {
                let row = `<tr>
                     <td>${orderDetails.orderId}</td>
                     <td>${orderDetails.itemCode}</td>
                     <td>${orderDetails.qty}</td>
                     <td>${orderDetails.unitPrice}</td>
                    </tr>`;

                $('#tblOrderDetails').append(row);
            }
        },
        error: function (error) {

        }
    });
}

function searchOrderDetail() {
    let orderId = $("#txtOrderDetailSearch").val();
    $("#tblOrderDetails").empty();
    $.ajax({
        type : "GET",
        url: "http://localhost:8080/backend/orderDetails?option=search&orderId=" + orderId,
        success : function (details) {
            console.log(details);
            console.log(details.orderId);

            let row =  `<tr>
                     <td>${details.orderId}</td>
                     <td>${details.itemCode}</td>
                     <td>${details.qty}</td>
                     <td>${details.unitPrice}</td>
                    </tr>`;

            $("#tblOrderDetails").append(row);
            $("#txtOrderDetailSearch").val("");
        },
        error : function (error) {
            Swal.fire({
                icon: "warning",
                title: "Oooops...",
                text: "No result found!..."
            })
        }
    });
}