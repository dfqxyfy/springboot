var rateMap=new Map();
let firstInit=false;
$(document).ready(function(){
    initBannerData();
    initCrytoData();
    $("#crypto").bind("click",function () {
        $("#exchangesTable").hide();
        $("#cryptoTable").show();
        $("#crypto").addClass("checked");
        $("#exchange").removeClass("checked");
        initCrytoData();
    });
    $("#exchange").bind("click",function () {
        $("#exchange").addClass("checked");
        $("#crypto").removeClass("checked");
        $("#cryptoTable").hide();
        $("#exchangesTable").show();
        initExchangesData();
    });
    $("#lang").change(swcurrency);
});

function swcurrency(){
    let coin = $("#lang").children('option:selected').val();
    switchCurrency(coin);
}
function initCrytoData(){
    $.ajax({
        url:"/api/getCryptoData",
        async:false,
        dataType: "json",
        success:function (htmlobj) {
            dealCry(htmlobj);
            swcurrency();
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
        rateMap.set(res[i].coin,res[i].rate);
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
        baseBody+=`<img align="center">
                     <td class="frist">${i+1}</td>
                     <td data_name="${obj.simpleName}"><img src="${obj.localUrl}"></img>${obj.simpleName}</td>
                     <td data_dollar="${obj.marketCap}">${obj.marketCap}</td>
                     <td data_dollar="${obj.price}">${obj.price}</td>
                     <td data_dollar="${obj.volume24}">${obj.volume24}</td>
                     <td data_dollar="${obj.circulatingSupply}">${obj.circulatingSupply}</td>
                     <td data_rate="${obj.change24}">${obj.change24}</td>
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
            swcurrency();
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
                     <td data_name="${obj.name}"><img src="${obj.localUrl}"></img>${obj.name}</td>
                     <td data_dollar="${obj.adjvol}">${obj.adjvol}</td>
                     <td data_dollar="${obj.volume24h}">${obj.volume24h}</td>
                     <td data_dollar="${obj.volume7d}">${obj.volume7d}</td>
                     <td data_dollar="${obj.volume30d}">${obj.volume30d}</td>
                     <td data_dollar="${obj.noMarkets}">${obj.noMarkets}</td>
                     <td data_rate="${obj.change24h}">${obj.change24h}</td>
                     <td data_name="${obj.launched}">${obj.launched}</td>
                     <td data_bak=""></td>
                 </tr>`;
    }
    $("#exchangesData").html(baseBody);
}

function switchCurrency(coin){
    let rate = rateMap.get("data-"+ coin);
    let pre = "$ ";
    let suffix="";
    switch(coin){
        case "cny":
            pre="￥";suffix=" ";break;
        case "usd":
            pre="$";suffix=" ";break;
        case "btc":
            pre="";suffix=" BTC";break;

    }
    $("td[data_dollar]").each(
        function (index,ele) {
            let money=$(ele).attr("data_dollar")/rate;

            if(money<1){
                money=money.toFixed(6);
            }else{
                money=money.toFixed(2);
            }
            ele.innerText=pre + " "+ toThousands(money) + suffix;
        }
    );
    $("td[data_rate]").each(
        function (index,ele) {
            let money=$(ele).attr("data_rate")/1;
            money=money.toFixed(2);
            ele.innerText= " "+ money + "%";
        }
    );

}


function toThousands(num) {
    var num = (num || 0).toString(),
        result = '';
//判断是否带小数点
    if (num.split('.')[1]) {
        var numInt = num.split('.')[0],
            numFlo = num.split('.')[1];
        result = formatter(numInt) + '.' + numFlo
    } else {
        result = formatter(num);
    }
    return result;

//格式化整数
    function formatter(numInt) {
        var resultInt = '';
        while (numInt.length > 3) {
            resultInt = ',' + numInt.slice(-3) + resultInt;
            numInt = numInt.slice(0, numInt.length - 3);
        }
        if (numInt) {
            resultInt = numInt + resultInt;
        }
        return resultInt;
    }
}