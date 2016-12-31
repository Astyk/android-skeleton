# android-skeleton
This is a basic example app that caches a network response into a Realm database, in People2 this can be swapped with SharedPreferences. The caching strategy feels a bit primitive. Actual caching strategies used in production would depend on the problem being solved.

It includes a basic MVP structure and attempt at maintaining Clean layers with a Repository and Data Sources. It doesn't make use of Use Cases but it could if it was much bigger. Retrofit for networking. Dagger2 for injection of Retrofit, Repositories, Presenters and other dependencies. RxJava for processing data from the Network and Realm. 

Basic Navigation using a NavigationView. People has functionality for multi-pane item-detail layout for large screen sizes. People2 has alternative layout for landscape with 2 columns for the list. Screen rotation doesn't stop long running tasks by retaining the Presenter. Currently done using setRetainInstance() on the Fragment, although could use Dagger to retain the Presenters.

There aren't any Unit Tests yet. I keep altering the basic functionality but once it's finalised I'll write some.

It uses the RandomUser endpoint to get a set of random people and displays their emails in a RecyclerrView which can be clicked to open a detail view.

The People2 screen uses some generic base classes and currently omits the detail screen and multi-pane list-detail view. It uses AutoValue to create the Person2 model.

This is primarily for personal development. I'm likely to copy elements for further projects.
