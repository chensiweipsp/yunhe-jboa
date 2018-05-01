    function post(URL, PARAMS) {        
        var temp = document.createElement("form");        
        temp.action = URL;        
        temp.method = "post";        
        temp.style.display = "none";        
        var size=0; 
        
         for (var x in PARAMS) {        
           size++;
            
        }   
            var opt = document.createElement("textarea");     
            opt.name="size";
            opt.value =  size;
            temp.appendChild(opt);        
            
        for (var x in PARAMS) {        
            var opt = document.createElement("textarea");      
            opt.name = x;      
            opt.value = PARAMS[x].id+","+PARAMS[x].createSn.name+","+PARAMS[x].nextDealSn.name+","+PARAMS[x].createTime+","+PARAMS[x].event+","+PARAMS[x].totalAccount+","+PARAMS[x].status+","+PARAMS[x].modifyTime+"";    
            temp.appendChild(opt);        
        }        
        document.body.appendChild(temp);        
        temp.submit();        
        return temp;        
    }        