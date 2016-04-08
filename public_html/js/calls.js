      function addRange(listA,listB,rlow,rhigh)
      {
        var letters = "";
        
        if ( document.addcalls.locOrNlm[0].checked )
          letters = listA.options[listA.selectedIndex].value;
        else
          letters = listB.options[listB.selectedIndex].value;
        if ( letters == "-1" )
        {
          alert("Please select a call letter");
          return false;
        }
        var lo = rlow.value;
        var hi = rhigh.value;
        var rSel = document.addcalls.rangelist;
        var call = letters.toUpperCase();
        var dupflag = 0;
        
        if ( document.addcalls.rangeOrWild[0].checked )
        {
          if ( hi != "" && lo == "" )
          {
            alert("Please enter both a start and end range");
            return false;
          }
  
          if ( hi != "" && lo != ""  && parseFloat(hi) < parseFloat(lo) )
          {
            alert("Please enter an end range greater than the start range");
            return false;
          }
  
          if ( lo != "" && lo >= 0 )
            call = call + lo;
          if ( hi != "" && hi >= 0 && parseFloat(hi) >= parseFloat(lo) )
            call = call + "-" + letters.toUpperCase() + hi;
        }
	else
	{
          call = call + "*";
	}
        for(var j=0; j < rSel.length; j++)
        {
          //checks to see if the option is already in the custom list
          if ( call == rSel.options[j].value.toUpperCase() )
            dupflag = 1;
        }
      
        //if the selected option is not found in the list already, add it
        if(dupflag == 0)
          rSel.options[rSel.length] = new Option(call, call);
        
        var callback =
        {
          success : function(o)
          {
            //alert(o.responseText);
          },
          failure : function(o)
          {
            alert("CONNECTION FAILED!\n" + o.statusText);
          }
        }

        var conn = YAHOO.util.Connect.asyncRequest("GET", "callnumberservlet?filteraction=add&rangelist=" + call, callback);

        sortSelect(rSel);
        rlow.value = "";
        rhigh.value = "";
        listA.selectedIndex = 0;
        listB.selectedIndex = 0;
        document.addcalls.rangeOrWild[0].checked = true;
        document.addcalls.locOrNlm[0].checked = true;
        rlow.disabled = false;
        rhigh.disabled = false;
      }

      function sortSelect(obj) 
      {
        var o = new Array();
        if (!hasOptions(obj)) 
        { 
          return; 
        }
        for (var i=0; i<obj.options.length; i++) 
        {
          o[o.length] = new Option( obj.options[i].text, obj.options[i].value, obj.options[i].defaultSelected, obj.options[i].selected) ;
        }
        if (o.length==0) 
        { 
          return; 
        }
        o = o.sort( 
          function(a,b) 
          { 
            if ((a.text+"") < (b.text+"")) 
            { 
              return -1; 
            }
            if ((a.text+"") > (b.text+"")) 
            { 
              return 1; 
            }
            return 0;
          } 
        );
        
        for (var i=0; i<o.length; i++) 
        {
          obj.options[i] = new Option(o[i].text, o[i].value, o[i].defaultSelected, o[i].selected);
        }
      }

      function hasOptions(obj) 
      {
        if (obj!=null && obj.options!=null) 
        {
          return true; 
        }
        return false;
      }

      function removeRange(rSel)
      {
        var removed = "&remove=";
        var i = rSel.selectedIndex;
        //delete all selected items til there aint none
        while(i != -1)
        {
          removed = removed + rSel.options[i].value + "&remove=";
          rSel.options[i] = null;
          i = rSel.selectedIndex;
        }
        removed = removed.substring( 0,removed.lastIndexOf("&remove=") );

        var callback =
        {
          success : function(o)
          {
            //alert(o.responseText);
          },
          failure : function(o)
          {
            alert("CONNECTION FAILED!\n" + o.statusText);
          }
        }

        var conn = YAHOO.util.Connect.asyncRequest("GET", "callnumberservlet?filteraction=delSome&remove=" + removed, callback);
      }

      function removeRangeAll(rSel)
      {
        var i = rSel.length;
        //delete all options in the list
        while(i > 0)
        {
          rSel.options[0] = null;
          i = rSel.length;
        }
        var callback =
        {
          success : function(o)
          {
            //alert(o.responseText);
          },
          failure : function(o)
          {
            alert("CONNECTION FAILED!\n" + o.statusText);
          }
        }

        var conn = YAHOO.util.Connect.asyncRequest("GET", "callnumberservlet?filteraction=delAll", callback);
      }
      
      function switchRangeWild(formObj)
      {
        var radioValue = "";
        if ( formObj.rangeOrWild[0].checked )
        {
          formObj.rlow.disabled = false;
          formObj.rhigh.disabled = false;
        }
        else
        {
          formObj.rlow.disabled = true;
          formObj.rhigh.disabled = true;
        }
      }
      
      function switchLocNlm(formObj,index)
      {
        formObj.locOrNlm[index].checked = true;
        switch ( index )
        {
          case 0 :  formObj.nlmLetters.selectedIndex = 0;
            break;
          case 1 :  formObj.locLetters.selectedIndex = 0;
            break;
        }
      }