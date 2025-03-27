** TO DO **

// add a user entry button in the course selection screen. when a grade has been saved previously the user should enter their name and view their grades and needed notes from the final (or whatever there is) without having to enter their grades every time.

// the user should be able to edit their grades after entering as a user. if you cant make this one just make the user enter normally and check if he has registered before. once the user enters some values to the calculator, check the course file (txt) if the user has saved any values before. if not save the new values. if yes update the values. to update them pass the values of the user (starting from the user's name, ends with the other user's name) to an array. iterate over the array to check if there are any changes. if there are update the array and sync(?) the array to the txt file. the file should be sth like this:
(if there is no final value passed enter 0)
elif 90 80 100 70 0 mete 95 85 100 80 97 ... (the array for elif should start from 90 and end at 0, including)

overall, add save logic and edit the course selection screen according to these updates. 
