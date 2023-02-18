# Goal
This app was created as a side project to help train drivers with their schedule.

## How does it work
The app uses specific algorithm to create shift schedule for a station.

## Input
For a working schedule, the user needs to manually add drivers and routes his station has.
There's a demonstration data, which can be created via "Fill DB for demonstration" menu option.
Similarly, this demonstration data can be deleted using "Clear DB" option from the same menu.

## Output
A working schedule for a month, with constraints (here's some of them):
- time period between jobs should be no less than 16 hours for a driver;
- a driver can't do night shifts two times in a row;
- if a driver became unavailable, the algorithm should keep schedule changes to a minimum;

## Technology and structure
- MVVM
- Koin
- Single Activity
- View Binding
- Material design

## Important note
The app doesn't log the schedule state at any given time. Instead, it always tries to keep the schedule as optimal as possible, since the purpose is to save an operator the headache to arrange everything manually. A separate log should always be used for the past routes documenting.

## SDK restrictions
Minimum SDK is 26.

## Localizations
- RU
