/**
 <tr id="id-bitcoin" class="">
 <td class="text-center">
 1
 </td>

 <td class="no-wrap currency-name" data-sort="Bitcoin">
 <img src="https://s2.coinmarketcap.com/static/img/coins/32x32/1.png"
 class="logo-sprite" alt="Bitcoin" height="16" width="16">
 <span class="currency-symbol visible-xs"><a class="link-secondary"
 href="/currencies/bitcoin/">BTC</a></span>
 <br class="visible-xs">
 <a class="currency-name-container link-secondary" href="/currencies/bitcoin/">Bitcoin</a>
 </td>

 <td class="no-wrap market-cap text-right" data-usd="1.1135152049e+11"
 data-btc="17692075.0" data-sort="1.1135152049e+11">
 $111,351,520,490
 </td>


 <td class="no-wrap text-right" data-sort="6293.86437092">
 <a href="/currencies/bitcoin/#markets" class="price" data-usd="6293.86437092"
 data-btc="1.0">$6293.86</a>
 </td>

 <td class="no-wrap text-right" data-sort="18078435975.1">
 <a href="/currencies/bitcoin/#markets" class="volume" data-usd="18078435975.1"
 data-btc="2877589.35881">$18,078,435,975</a>
 </td>

 <td class="no-wrap text-right circulating-supply" data-sort="17692075.0">
 <span data-supply="17692075.0">
 <span data-supply-container>17,692,075</span>
 <span class="hidden-xs">BTC</span>
 </span>
 </td>

 <td class="no-wrap percent-change  positive_change  text-right" data-timespan="24h"
 data-percentusd="3.23534" data-symbol="BTC" data-sort="3.23534">3.24%
 </td>
 <td><a href="/currencies/bitcoin/#charts">
 <img class="sparkline" alt="sparkline"
 src="https://s2.coinmarketcap.com/generated/sparklines/web/7d/usd/1.png">
 </a></td>
 <td class="dropdown" data-more-options data-cc-id="1" data-cc-slug="bitcoin">
 <button class="btn btn-transparent dropdown-toggle" type="button"
 id="dropdown-menu-1" data-toggle="dropdown">
 <span class="glyphicons glyphicons-more text-gray"></span>
 </button>
 <ul class="dropdown-menu dropdown-menu-right" role="menu"
 aria-labelledby="dropdown-menu-1">
 <li role="presentation"><a role="menuitem" tabindex="-1" href="#"
 data-watchlist-add>Add to Watchlist</a></li>
 <li role="presentation"><a role="menuitem" tabindex="-1" href="#"
 data-watchlist-remove>Remove from Watchlist</a></li>
 <li class="disabled" role="presentation"><a role="menuitem" tabindex="-1"
 href="#" data-watchlist-full>Watchlist
 full!</a></li>
 <li role="presentation"><a role="menuitem" tabindex="-1"
 href="/currencies/bitcoin/#charts">View Chart</a>
 </li>
 <li role="presentation"><a role="menuitem" tabindex="-1"
 href="/currencies/bitcoin/#markets">View Markets</a>
 </li>
 <li role="presentation"><a role="menuitem" tabindex="-1"
 href="/currencies/bitcoin/historical-data/">View
 Historical Data</a></li>
 </ul>
 </td>
 </tr>

 **/


$(document).ready(function(){
    $.ajax({url:"/api/getCryptoData",
        async:false,
        dataType: "json",
        success:function (htmlobj) {
            deal(htmlobj);
        }
    });


});

function deal(htmlobj){
    var list = htmlobj.data;
    var baseBody="";
    for(var i=0;i<list.length;i++){
        var obj=list[i];

        baseBody+="<tr id=\"\" class=\"\">";
        baseBody+="<td class=\"text-center\">\n" +
            (i+1) +
            " </td>";
        baseBody+=
            "<td class=\"no-wrap currency-name\" data-sort=\"Bitcoin\">\n" +
            " <img src=\"\"\n" +
            " class=\"logo-sprite\" alt=\""+obj.name+"\" height=\"16\" width=\"16\">\n" +
            " <span class=\"currency-symbol visible-xs\"><a class=\"link-secondary\"\n" +
            " href=\"\">"+obj.simpleName+"</a></span>\n" +
            " <br class=\"visible-xs\">\n" +
            " <a class=\"currency-name-container link-secondary\" href=\"/\">Bitcoin</a>\n" +
            " </td>";

        baseBody+=
            " <td class=\"no-wrap market-cap text-right\" data-usd=\""+obj.marketCap+"\"\n" +
            " data-btc=\""+ obj.marketCap +"\" data-sort=\""+obj.marketCap +"\">\n" +
            obj.marketCap +
            " </td>"
        ;
        baseBody+=
            "<td class=\"no-wrap text-right\" data-sort=\""+obj.price+"\">\n" +
            " <a href=\"\" class=\"price\" data-usd=\""+obj.price+"\"" +
            " data-btc=\"1.0\">"+obj.price+"</a>\n" +
            " </td>";

        baseBody+=
            "<td class=\"no-wrap text-right\" data-sort=\""+obj.volume24+"\">\n" +
            " <a href=\"\" class=\"volume\" data-usd=\""+obj.volume24 +"\""
        " data-btc=\""+obj.volume24 +"\">"+obj.volume24 +"</a>" +
        " </td>" ;

        baseBody+=
            "<td class=\"no-wrap text-right circulating-supply\" data-sort=\""+obj.circulatingSupply+"\">\n" +
            " <span data-supply=\""+obj.circulatingSupply+"\">\n" +
            " <span data-supply-container>"+obj.circulatingSupply+"</span>\n" +
            " <span class=\"hidden-xs\">"+obj.simpleName+"</span>\n" +
            " </span>\n" +
            " </td>";


        baseBody+=
            "<td class=\"no-wrap percent-change  positive_change  text-right\" data-timespan=\"24h\"\n" +
            " data-percentusd=\""+obj.change24+"\" data-symbol=\""+obj.simpleName+"\" data-sort=\""+obj.change24+"\">"+obj.change24+"" +
            " </td>\n" +
            " <td><a href=\"/currencies/bitcoin/#charts\">\n" +
            " <img class=\"sparkline\" alt=\"sparkline\"\n" +
            " src=\"\">\n" +
            " </a></td>";

        // obj.change24;
        // obj.circulatingSupply;
        // obj.marketCap;
        // obj.name;
        // obj.nameImg;
        // obj.num;
        // obj.price;
        // obj.simpleName;
        // obj.volume24;
        baseBody+="</tr>";

    }

    $("#cryptocurrencies_data").html(baseBody);
}