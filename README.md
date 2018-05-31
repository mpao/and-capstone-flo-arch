# Florence '900 Architectures

Capstone project for udacity android nanodegree, based on Opendata by [opendata.comune.fi.it](http://opendata.comune.fi.it/)

> Architecture of the 1900s<br/>
> The City of Florence's database on 20th-century architecture is a fundamental 
> tool for the knowledge of the most significant buildings of the city's architecture.<br/>
>
> [source](http://opendata.comune.fi.it/cultura_turismo/dataset_0363.html)

### Constraints

1.  App is written solely in the **Java** Programming Language
2.  App utilizes **stable release versions** of all libraries, Gradle, and Android Studio.
3.  App includes support for **accessibility**, **RTL**, and other languages
4.  App provides a **widget** to provide relevant information to the user on the home screen.
5.  App integrates two or more **Google services**, I choose Map and Admob
6.  if **Admob** is used, the app displays test ads
7.  if **Maps** is used, the map provides relevant information to the user
8.  Each service imported in the build.gradle is **used** in the app.
9.  All app dependencies are **managed by Gradle**.
10.  App theme extends **AppCompat**.
11. App uses an **app bar** and associated toolbars.
12. App builds and deploys using the **installRelease Gradle task**.
13. App is equipped with a **signing configuration**, and the **keystore** and passwords are included in the repository. Keystore is referred to by a relative path. 
14. App stores data locally by implementing a **ContentProvider**
15. If it needs to pull or send data to/from a web service or API only once, or on a per request basis (such as a search application), app uses an **IntentService** to do so.
16. App uses a **Loader** to move its data to its views(unless you are using Firebase).


### Documentations

- Full specifications for project are [in this pdf](docs/rubrics.pdf)
- Capstone part1 with app description, UI flow mocks, list of required tasks is [in this pdf](docs/Capstone_Stage1.pdf)







