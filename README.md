# GitRepoBrowsApp
 Demo project built with Flow, Coroutine MVVM

The following is an android demonstration application used to show the usage of MVVM architecture along with Coroutine Flow, livedata. The Application uses the Gihub Open Api v3 to request the list of public repositories with the language reference <code>Kotlin</code> in them. 

<br/>

The Applications makes use of 

<br/>

- <code>ViewModel</code> which acts as a controller to the activity
- <code>Repository</code> which acts as a network data provider (Since the concept of local cache is not valid here)
- <code>Dagger2</code> which allows dependency injections across the application
- <code>Retrofit</code> which the rest api client used in the to make network calls
- <code>Picasso</code> for image loading
- <code>Coroutines</code> for asynchronous programming
