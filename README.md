# Detechxify2


About the Project
--------------------------
As of 2018, one of the most common forms of addiction that nearly every millenial is suffering with is phone addiction. The worst part is that unlike other forms of addiction most people fail to realize the existence of this addiction and don't consider it a big deal. Most millenials spend a fairly large portion of their day unmindfully scrolling through Instagram of Facebook posts. 
This application lets users track and curb the usage of addictive apps on their phone such as Instagram, Facebook, Snapchat and so on. Our application uses User Stats Manager that provides access to device usage history and statistics and gives user an idea of their most frequently visited apps. Usage data is aggregated into time intervals: days, weeks, months, and years. This data will help the user identify the apps that they have been spending the most time on. 
The user can then select the applications whose usage they want to restrain. They can 'take a pledge' and set a maximum limit of time they would like to allocate to that app for a day. Our app then runs as a background process and keeps track of the number of hours spent on that app. 
We have further added a reward system to make fighting addiction fun. Our app does not block the 'marked app' if the user crosses the time he/she had allocated for that app because we understand that apps like Facebook often acts as critical means of communiation in times of emergency. On the other hand we offer badges for keeping up to their pledge. We also provide progress bar to track their progress. 

How to test it
------------------------------
Upon installing the app, a user has to create a Login - 
1) They can set it up manually 
2) They can login with Facebook

For first time users, they'll be shown their most frequently used apps. 
They can then choose the apps from a dropdown whose usage they want to curb and proceed to 'TAKE a PLEDGE' 

Upon the completion of the pledge, the user is informed of the result and the progress graph gets updated. 

RESTful Webservice
-------------------------------
The RESTful Webservice for this was written with SpringBoot and can be found https://github.com/sadhikari0102/detech-api 
