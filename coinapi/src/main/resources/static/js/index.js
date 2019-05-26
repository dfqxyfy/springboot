$(document).ready(function(){
    initBannerData();
    initCrytoData();
    $("#crypto").bind("click",function () {
        $("#exchangesTable").hide();
        $("#cryptoTable").show();
        $("#cryptoTable").addClass("checked");
        $("#exchangesTable").removeClass("checked");
        initCrytoData();
    });
    $("#exchange").bind("click",function () {
        $("#exchangesTable").addClass("checked");
        $("#cryptoTable").removeClass("checked");
        $("#cryptoTable").hide();
        $("#exchangesTable").show();
        initExchangesData();
    });
});
function initCrytoData(){
    $.ajax({
        url:"/api/getCryptoData",
        async:false,
        dataType: "json",
        success:function (htmlobj) {
            dealCry(htmlobj);
        }
    });
}

function initBannerData(){
    $.ajax({
        url:"/api/initData",
        async:false,
        dataType: "json",
        success:function (res) {
            initGlobalData(res.data.totalData);
        }
    });
}

function initGlobalData(data){
    $("#crypto").html(data.cryptocurrencies);
    $("#markets").html(data.markets);
    $("#marketCap").html(data.marketCap);
    $("#vol24h").html(data.vol24h);
    $("#btcDominance").html(data.btcDominance);
}

function dealCry(htmlobj){
    var list = htmlobj.data;
    var baseBody="";
    for(var i=0;i<list.length;i++){
        var obj=list[i];
        baseBody+=`<tr align="center">
                     <td class="frist">${i+1}</td>
                     <td>${obj.simpleName}</td>
                     <td>${obj.marketCap}</td>
                     <td>${obj.price}</td>
                     <td>${obj.volume24}</td>
                     <td>${obj.circulatingSupply}</td>
                     <td>${obj.change24}</td>
                     <td></td>
                 </tr>`;
    }
    $("#cryptoData").html(baseBody);
}

function initExchangesData() {
    $.ajax({url:"/api/getExchanges",
        async:false,
        dataType: "json",
        success:function (htmlobj) {
            dealExchange(htmlobj);
        }
    });
}
function dealExchange(htmlobj) {
    var list = htmlobj.data;
    var baseBody="";
    var baseBody="";
    for(var i=0;i<list.length;i++){
        var obj=list[i];
        baseBody+=`<tr align="center">
                     <td class="frist">${i+1}</td>
                     <td>${obj.name}</td>
                     <td>${obj.adjvol}</td>
                     <td>${obj.volume24h}</td>
                     <td>${obj.volume7d}</td>
                     <td>${obj.volume30d}</td>
                     <td>${obj.noMarkets}</td>
                     <td>${obj.change24h}</td>
                     <td>${obj.launched}</td>
                     <td></td>
                 </tr>`;
    }
    $("#exchangesData").html(baseBody);
}


