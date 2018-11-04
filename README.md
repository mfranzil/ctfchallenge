# CTFChallenge

**CTFChallenge** is a simple Java-FX driven project made for a local cryptography *Capture the Flag* event.
It supports a customizable number of teams and rounds.


## Dependencies

Dependencies are placed in the /lib folder. Remember to manually add them to your Maven project or to your IDE dependencies in order for CTFChallenge to build correctly. The only JAR used at the moment is `json-simple-1.1.1.jar`.


## Features

* Addition of unlimited teams, consisting of two people, an unique team name and a score initially set to 0.
* Seamless removal (only before match start) and editing of teams, including score corrections for each team.
* Assignment of points to teams who successfully capture the flag, and bonus points for teams ranking in the first
  *n* positions (customizable).
* A log area providing useful information on what's happening or shouldn't be happening.
* Separate window with real-time updating scoreboard and buttons for editing font size.


## Customization

In the Common.java file, you will find two final variables to customize:
* **MAX_TEAMS_BONUS** - Maximuim points the first team will receive. If greater than the number of teams, the number
  of teams will be used instead.
* **MAX_ROUNDS** - The number of rounds in the match. You should edit the **getScore** method to accomodate any
  changes.

## Authors

* **Matteo Franzil** - *Initial work* - [mfranzil](https://github.com/mfranzil)
* **Christian Spolaore** - *Initial work* - [chris797](https://github.com/chris797)


## License

This project is open source. Please, contact me for suggestions and reviews.
