# Goal
This app was created as a side project to help train drivers with their schedule.

## How does it work
The app uses specific algorithm to create shift schedule for a station.

## Input
For a working schedule, the user needs to manually add drivers and routes his station has.
There's a demonstration data, which can be created via "Fill DB for demostration" menu option.
Similarly, this demonstration data can be deleted using "Clear DB" option from the same menu.

## Output
A working schedule for a month, with constraints (here's some of them):
- time period between jobs should be no less than 16 hours for a driver;
- a driver can't work during night hours more than 2 days in a row;
- if a driver becomes unavailable, the algorithm should keep changes in the schedule to a minimum;
