      function validateAdd(formObj)
      {
        if ( isNaN(formObj.library.length) )
        {
          if ( formObj.library.checked == false )
          {
            alert("Please select at least one library to add");
            return false;
          }
        }
        else
        {
          var count = 0;
          for ( var i = 1; i <= formObj.library.length; i++ )
          {
            if ( formObj.library[ i - 1 ].checked == true )
              count++;
          }
          if ( count == 0 )
          {
            alert("Please select at least one library to add");
            return false;
          }
        }
        return true;
      }
      
      function validateDel(formObj)
      {
        if ( isNaN(formObj.remove.length) )
        {
          if ( formObj.remove.checked == false )
          {
            alert("Please select at least one library to remove");
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
            alert("Please select at least one library to remove");
            return false;
          }
        }
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
