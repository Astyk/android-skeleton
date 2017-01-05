# android-skeleton
This is a basic example app that caches a network response into a Realm database, in People2 this can be swapped with SharedPreferences. The caching strategy feels a bit primitive, it gets from Storage, if anything, then from the Network and updates the Storage each time we open a screen. Actual caching strategies used in production would depend on the problem being solved.

It includes a basic MVP structure and attempt at maintaining Clean layers with a Repository and Data Sources. It doesn't make use of Use Cases, I am yet to play with a fully Clean Architecture. Retrofit for networking. Dagger2 for injection of Retrofit, Repositories, Presenters and other dependencies. RxJava for processing data from the Network and Realm. 

Basic Navigation using a NavigationView. People has functionality for multi-pane item-detail layout for large screen sizes. People2 has alternative layout for landscape with 2 columns for the list. Screen rotation doesn't stop long running tasks as we retain the Presenter. Currently done using setRetainInstance() on the Fragment, although could use Dagger.

There aren't any Unit Tests but it's generally built in a way that would allow for them. Anywhere I have used static functions/classes in Activities or Fragments I'm aware I should probably inject them with Dagger.

It uses the RandomUser endpoint to get a set of random people and displays their emails in a RecyclerView which in the first People screen can be clicked to open a detail view.

The People2 screen uses some generic base classes and currently omits the detail screen and multi-pane list-detail view. It uses AutoValue to create the Person2 model and converters for the Realm model.

This is primarily for personal development. However, I'm likely to copy elements of it for future projects.
