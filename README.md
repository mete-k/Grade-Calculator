** TO DO **

//complete the calculate button. When the final grade is entered as "x" (or anything other than a double or an int), calculate the grade the user needs to get from the final for a specific average (in table form)
ex:
  90.0  76
  85.0  71
  80.0  66
  ...
if the course is catalog instead of averages just enter the letter grades. else enter the averages starting from the largest value that is less than 0 (for example the user should get 110 for an a and 80 for an a-, the table should start from a-. (maybe add a button to show more ?? ) do not print any grade below 0 or over 100.

// add a user entry button in the course selection screen. when a grade has been saved previously the user should enter their name and view their grades and needed notes from the final (or whatever there is) without having to enter their grades every time.

// the user should be able to edit their grades after entering as a user. if you cant make this one just make the user enter normally and check if he has registered before. once the user enters some values to the calculator, check the course file (txt) if the user has saved any values before. if not save the new values. if yes update the values. to update them pass the values of the user (starting from the user's name, ends with the other user's name) to an array. iterate over the array to check if there are any changes. if there are update the array and sync(?) the array to the txt file. the file should be sth like this:
(if there is no final value passed enter 0)
elif 90 80 100 70 0 mete 95 85 100 80 97 ... (the array for elif should start from 90 and end at 0, including)

overall, add save and calculate logic and edit the course selection screen according to these updates. 
