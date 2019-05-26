var rateMap=new Map();
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
            initRate(res.data.listRateData);
        }
    });
}

function initRate(res) {
    for(var i=0;i<res.length;i++){
        rateMap.set(res[i].coin,rate);
    }
}

function initGlobalData(data){
    $("#cryptocurrencies").html(data.cryptocurrencies);
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
                     <td data_dollar="${obj.simpleName}">${obj.simpleName}</td>
                     <td data_dollar="${obj.marketCap}">${obj.marketCap}</td>
                     <td data_dollar="${obj.price}">${obj.price}</td>
                     <td data_dollar="${obj.volume24}">${obj.volume24}</td>
                     <td data_dollar="${obj.circulatingSupply}">${obj.circulatingSupply}</td>
                     <td data_dollar="${obj.change24}">${obj.change24}</td>
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
                     <td data_dollar="${obj.name}">${obj.name}</td>
                     <td data_dollar=">${obj.adjvol}">${obj.adjvol}</td>
                     <td data_dollar="${obj.volume24h}">${obj.volume24h}</td>
                     <td data_dollar="${obj.volume7d}">${obj.volume7d}</td>
                     <td data_dollar="${obj.volume30d}">${obj.volume30d}</td>
                     <td data_dollar="${obj.noMarkets}">${obj.noMarkets}</td>
                     <td data_dollar="${obj.change24h}">${obj.change24h}</td>
                     <td data_dollar="${obj.launched}">${obj.launched}</td>
                     <td data_dollar=""></td>
                 </tr>`;
    }
    $("#exchangesData").html(baseBody);
}


