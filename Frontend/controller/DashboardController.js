initUI();

function clearAll() {
    $("#homeContent, #customerContent, #itemContent, #orderContent, #orderHistory").css("display", "none");
}

function initUI() {
    clearAll();
    $("#homeContent").css("display","block");
    setLastView();
}

function setLastView() {
    let view = localStorage.getItem("view")
    switch (view){
        case "HOME":
            setView($("#homeContent"));
            break;
        case "CUSTOMER":
            setView($("#customerContent"));
            break;
        case "ITEM":
            setView($("#itemContent"));
            break;
        case "ORDER":
            setView($("#orderContent"));
            break;
        case "ORDER_HISTORY":
            setView($("#orderHistory"));
            break;
        default:
            setView($("#homeContent"));
    }
}

function setView(viewObject) {
    clearAll();
    viewObject.css("display","block");
    saveLastView(viewObject.get(0).id);
    console.log(viewObject.get(0).id);
}

function saveLastView(id) {
    switch (id){
        case "homeContent":
            localStorage.setItem("view", "HOME");
            break;
        case "customerContent":
            localStorage.setItem("view", "CUSTOMER");
            break;
        case "itemContent":
            localStorage.setItem("view", "ITEM");
            break;
        case "orderContent":
            localStorage.setItem("view", "ORDER");
            break;
        case "orderHistory":
            localStorage.setItem("view", "ORDER_HISTORY");
            break;
    }
}

// --bindEvents__
$("#linkHome").click(function (){
    setView($("#homeContent"));
})

$("#linkCustomer").click(function (){
    setView($("#customerContent"));
})

$("#itemLink").click(function (){
    setView($("#itemContent"));
})

$("#orderLink").click(function (){
    setView($("#orderContent"));
})

$("#orderHistoryLink").click(function (){
    setView($("#orderHistory"));
})


