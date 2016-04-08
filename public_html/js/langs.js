  function hasOptions(obj) 
  {
    if (obj!=null && obj.options!=null) 
    { 
      return true; 
    }
    return false;
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
      o[o.length] = new Option( obj.options[i].text, 
                                obj.options[i].value, 
                                obj.options[i].defaultSelected, 
                                obj.options[i].selected) ;
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
      obj.options[i] = new Option(o[i].text, 
                                  o[i].value, 
                                  o[i].defaultSelected, 
                                  o[i].selected);
    }
  }

  function selectAllOptions(obj) 
  {
    if (!hasOptions(obj)) 
    { 
      return; 
    }
    for (var i=0; i<obj.options.length; i++) 
    {
      obj.options[i].selected = true;
    }
  }

  function addSelectedOptions(from, to, includeExclude) 
  {
    var query="filteraction=add&";
    
    if ( includeExclude[0].checked )
      query = query + "includeExclude=true&";
    else
      query = query + "includeExclude=false&";
  
    // Move them over
    for (var i=0; i<from.options.length; i++) 
    {
      var o = from.options[i];
      if (o.selected) 
      {
        if (!hasOptions(to)) 
        { 
          var index = 0; 
        } 
        else 
        { 
          var index=to.options.length; 
        }
        to.options[index] = new Option( o.text, o.value, false, false);
        query = query + "langs=" + o.value + "&";
      }
    }
    // Delete them from original
    for (var i=(from.options.length-1); i>=0; i--) 
    {
      var o = from.options[i];
      if (o.selected) 
      {
        from.options[i] = null;
      }
    }
    sortSelect(from);
    sortSelect(to);
  
    from.selectedIndex = -1;
    to.selectedIndex = -1;
  
    query = query.substring( 0,query.lastIndexOf("&") );
    //alert(query);

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
    
    var conn = YAHOO.util.Connect.asyncRequest("GET", "languageservlet?" + query, callback);
  }

  function removeSelectedOptions(from, to, includeExclude) 
  {
    var query="filteraction=delSome&";
    
    if ( includeExclude[0].checked )
      query = query + "includeExclude=true&";
    else
      query = query + "includeExclude=false&";
    
    // Move them over
    for (var i=0; i<from.options.length; i++) 
    {
      var o = from.options[i];
      if (o.selected) 
      {
        if (!hasOptions(to)) 
        { 
          var index = 0; 
        } 
        else 
        { 
          var index=to.options.length; 
        }
        to.options[index] = new Option( o.text, o.value, false, false);
        query = query + "remove=" + o.value + "&";
      }
    }
    // Delete them from original
    for (var i=(from.options.length-1); i>=0; i--) 
    {
      var o = from.options[i];
      if (o.selected) 
      {
        from.options[i] = null;
      }
    }
    sortSelect(from);
    sortSelect(to);
  
    from.selectedIndex = -1;
    to.selectedIndex = -1;
  
    query = query.substring( 0,query.lastIndexOf("&") );
    //alert(query);
  
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
    
    var conn = YAHOO.util.Connect.asyncRequest("GET", "languageservlet?" + query, callback);
  }

  function removeAllOptions(from,to) 
  {
    var query="filteraction=delAll";
  
    selectAllOptions(from);
    for (var i=0; i<from.options.length; i++) 
    {
      var o = from.options[i];
      if (o.selected) 
      {
        if (!hasOptions(to)) 
        { 
          var index = 0; 
        } 
        else 
        { 
          var index=to.options.length; 
        }
        to.options[index] = new Option( o.text, o.value, false, false);
      }
    }
    // Delete them from original
    for (var i=(from.options.length-1); i>=0; i--) 
    {
      var o = from.options[i];
      if (o.selected) 
      {
        from.options[i] = null;
      }
    }
    sortSelect(from);
    sortSelect(to);
  
    from.selectedIndex = -1;
    to.selectedIndex = -1;
  
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
    
    var conn = YAHOO.util.Connect.asyncRequest("GET", "languageservlet?" + query, callback);
  }

  function validateAdd(formObj)
  {
    if ( formObj.langs.length == 0 || isNaN(formObj.langs.length) )
    {
      alert("Please add at least one language to the Selected Languages list");
      return false;
    }
    for ( var i = 0; i < formObj.langs.length; i++ )
      formObj.langs.options[i].selected = true;
    return true;
  }

  function check(input, field)
  {
    if ( input.checked == true )
    {
      if ( isNaN(field.length) )
      {
        field.checked = true;
      }
      else
      {
        for ( i = 1; i <= field.length; i++)
        {
          field[i - 1].checked = true;
        }
      }
    }
    else
    {
      if ( isNaN(field.length) )
      {
        field.checked = false;
      }
      else
      {
        for ( i = 1; i <= field.length; i++)
        {
          field[i - 1].checked = false;
        }
      }
    }
  }

  function validateDel(formObj)
  {
    if ( isNaN(formObj.remove.length) )
    {
      if ( formObj.remove.checked == false )
      {
        alert("Please select at least one language to remove");
        return false;
      }
    }
    else
    {
      var count = 0;
      for ( var i = 1; i <= formObj.remove.length; i++ )
      {
        if ( formObj.remove[ i - 1 ].checked == true )
          count++;
      }
      if ( count == 0 )
      {
        alert("Please select at least one language to remove");
        return false;
      }
    }
    return true;
  }
  
  function verifyIncExc(radioObj, compObj, firstTime)
  {
    var chosen = "";
    if ( firstTime.value == "false" )
    {
      for ( i = 0; i < radioObj.length; i++ )
      {
        if ( radioObj[i].checked )
        {
          chosen = radioObj[i].value;
        }
      }
      if ( chosen != compObj.value )
        alert("Please note: you're changing the Limit To/Exclude value.\nThis change will be applied to any previously submitted languages.");
    }
  }
