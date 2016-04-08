      function validateAdd(formObj)
      {
        formObj.years.value = formObj.years.value.replace(/\s/g, "");
        if ( formObj.years.value != "" )
        {
          var yearArray = new Array();
          yearArray = formObj.years.value.split(/;|,|\//);
          for ( var i = 0; i < yearArray.length; i++ )
          {
            if ( ! /^\d{1,4}$|^\d{1,4}-\d{1,4}$|^\+\d{1,4}$|^-\d{1,4}$|^\d{1,4}-$/.test(yearArray[i]) ) //all forms once
            {
              alert("Your year value" + yearArray[i] + " does not seem to have the proper format");
              return false;
            }
          }
          return true;
        }
        else
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
            alert("Please select at least one year to remove");
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
            alert("Please select at least one year to remove");
            return false;
          }
        }
        return true;
      }
