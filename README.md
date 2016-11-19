# android-skeleton
This is a basic example app that caches a network response into a Realm database. The caching strategy feels a bit primitive. Actual caching strategies used in production would depend on the problem being solved.

It includes a basic MVP structure and attempt at maintaining Clean layers with a Repository and Data Sources. It uses Dagger2 for injection of Presenters. Retrofit for networking. RxJava for processing data from the Network and Realm. Basic Navigation using a NavigationView and functionality for having multi-pane item-detail layout.

It uses the RandomUser endpoint to get a set of random people and displays their emails in a RecylcerView which can be tapped to open a detail view.

I expect I'll copy and build on it but it's primarily for personal development.
