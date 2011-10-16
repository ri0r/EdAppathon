Batman won't die unless you read this!


16/10 -Roberto (going to focus on the database stuff)
+Made some changes to Location, (don't say getLocation...that shit is vague, getLocationName())
+Also looking to fix Database, but need to talk to Austin about what he's doing with Accessor.
+Can then populate listView

-Trying to populate previous routes listView with random stuff, but it won't show up, maybe a layout problem, debug statement says its fine.
-Either way the database stuff will be displayed there (most recent entries / most common);


16/10 - Oleg

Updates about current status of our app:
Got a controller class (used to be called Utility).
Getting roads for the route from Google Navigation.
mainActivity now uses controller for getting list of feeds.
Only relevant feeds for the route are being displayed.

What doesn't work:
ChooseRoute needs to pass user input to controller(for testing purposes start and end are hardcoded atm), easy to fix.
Extract GPS coordinates from the RSS feed (from the relevant entries, they are in the URL), probably not too hard.
Make them available to map activity when it starts. 
Make Twitter feed work, might have to wait with that unless someone has lots of time.
Route history from the database needs to be implemented, I think Austin is working on it? Currently we are getting roads from Google Navigation every time.
Preferences.
Stuff that I can't remember atm.

Once that is done we can start worrying about making it look pretty. Should probably leave whole next Sunday for this.