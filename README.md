##CIS 542 lab3 README

    Name: Zhi Li
    Android SDK: Android 4.2.2, API Level 17
    Android studio: Android studio(beta) 0.8.9
    Host Operating System: Mac OSX 10.10 Yosemite
    First Android App

##Notice: 
  1. If you are using emulator, make sure to specify the size of external storage ("SD Card") properly, since the default is 0. (could set it to 200 MiB) 
  2. Since I save the player statistics using Hashmap then write the hashmap to user1.ser, you might not preview what's inside the saved file. However, what I want to mention is that, everytime the app starts, it'll try to read the serialized hashmap within saved file and decode it for score display if the file exists. Otherwise it'll create a new file and save game statistics in it whenever a player hit "score" button or "quit" button.
  3. When testing on Emulator, I used Nexus 4(4.7'', 768*1280:xhdpi) on Intel Atom(x86) CPU core, platform Android 4.2.2 with API level 17. You might want to use the same resolution or else the button may either be too big/small.
  4. Don't know why Dialog.setCanceledOnTouchOutside(false) can't be resolved under the set environment, since Android Developer documents metioned this function to be included in API level 1.

##How to play this game:
  1. Input name and Login
  2. Pick a level to start(4,7,10 words for easy, medium, hard)
  3. Press on yes when ready to start timer
  4. Input the random sentence generated in text box and click submit.
  5. If you've played it for once, you can check the score list by pressing on score.
  6. The score screen will show the latest two players' score,best/worst time for each level. All players' statistics are stored in file user1.ser at external storage. 
  7. You could change level in run time.
  8. You could logout and input another user name to login. (If the new user share the same name as a former user stored in file, the new user will be assigned a new ID, while the former user's ID remains unchanged and name followed by "_old".
  9. Press quit to exit, the game statistics are stored in
  10. The game is tested on a real android device(Neutab i7, Android 4.4.2), it has also passed the test on emulator.
