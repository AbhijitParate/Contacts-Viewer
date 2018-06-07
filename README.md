# Contacts Viewer

## Architecture
Application uses MVP architecture which makes classes decoupled from each other and more unit testable.
Each screen is divided into two complimenting classes as View and Presenter.
Presenter contains all the logic for displaying everything in the passive View (Activity).
The source of contacts is the ContactStore class which act as Repository for the contact and is implemented as singleton.
So, contacts are present in the memory, so any changes made wont persist once the application is closed.

This whole setup with MVP and Repository helps unit test the application with better code coverage at the same time giving us control over the test date.

Currently there is only one flavor is available for application,
but we can take advantage of the repository pattern to create different versions of the contact store based on the build type.

