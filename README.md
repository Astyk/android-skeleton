# android-skeleton
This is a basic example app that caches a network response into a Realm database. The caching strategy feels a bit primitive. Actual caching strategies used in production would depend on the problem being solved.

It includes a basic MVP structure and attempt at maintaining Clean layers with a Repository and Data Sources. It doesn't make use of Use Cases but it could if it was much bigger. Retrofit for networking. Dagger2 for injection of Retrofit, Repositories and Presenters. RxJava for processing data from the Network and Realm. Basic Navigation using a NavigationView and functionality for having multi-pane item-detail layout.

There aren't any Unit Tests yet. I keep altering the basic functionality but once it's finalised I'll write some.

It uses the RandomUser endpoint to get a set of random people and displays their emails in a RecylcerView which can be clicked to open a detail view.

I expect I'll copy and build on it but it's primarily for personal development.
