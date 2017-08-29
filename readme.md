# FMC-Display
This is an experimental project aimed to explore the potential for an easily adaptable and portable Flight Management Computer (FMC) for flight simulation as a mean of flight tracking.

### Pending Revision to Program Flow

```
`static Main`
      |--Load dependencies, initializing:
      |   -Global variables, e.g. `Aircraft, World, Navigation`
      |   -Database, e.g. `Airport, Procedure, Navaids`
      |
----->|--`screen.render()`
|  |  |
|  --<|--`takeInput()`
-----<|   -Update corresponding Objects if input is valid
```

#### Objectives of Revision:
-Reduce dependencies between layers of program


## Current Project Organization
Brief explanation of the program's mechanics, and a description of relationships between files
### Mechanics


### Interfaces
`Waypoint`,
Implemented by:
-`Fix`, a defined aeronautical waypoint containing an `id`, and a `coordinate`
-`Navaid`, a defined waypoint of a ground-based navigatable station
-`Intercept`, a non-geographical waypoint that specifies a interception patah
-`Track`, a non-geographical waypoint specifies a numerical degree track
-`Vectors`, a non-geographical waypoint that specifies a numerical degree heading


### Classes, Non-Implementing
`NavigationDatabase`

`Airport`, a container object that encapsulates the features of an airport in terms of instrument procedures and geographical information.
Uses:
-`Runway`
-`Procedure`

`FlightPlanWaypoint`, a waypoint used exclusively for FMC's navigational planning capabilities;
`ProcedureWaypoint`,
##### Atomic Classes
`Coordinate`




## Notes to Self
Section to keep ideas organized and myself sane!
### Ideas


### Sample text-doc for Static Text Setup
;ident page
SPACE 10 IDENT SPACE 9
SPACE 1 MODEL SPACE 8 ENG SPACE 1 RATING
READ AC-MODEL 10 SPACE 2 READ AC-ENGRAT 12
SPACE 1 NAV SPACE 1 DATA SPACE 9 ACTIVE
READ NV-DCYCLE 10 SPACE 1 READ NV-ACTIVE 11
SPACE 24
SPACE 24
SPACE 24
SPACE 24
SPACE 17 DRAG/FF
------------------------
PAGE <INDEX SPACE 9 PAGE POS-INIT>

KEY,DESCRIPTION,SOURCE,TYPE,SPACES,JUSTIFIED
AC-MODEL,AIRCRAFT MODEL,READ-ONCE,STRING,10,LEFT
AC-ENGRAT,ENGINE RATING,READ-ONCE,STRING,12,RIGHT
NV-DCYCLE,NAVIGATION DATA,READ-ONCE,STRING,10,LEFT
NV-DEFF,NAV DATA EFFECTIVE DATE,READ-ONCE,STRING,13,RIGHT




## Features to Implement

### Dynamic Warning Messages
`NAV DATA OUT OF DATE`
`ROUTE 1 UPLINK READY`

### Design
-click on side button, returns position

## Miscellaneous
-load preset with formatting strings
-placeholder
-loading


## Screens Mock Ups

```
Conventions:
Load from Objects::  XX (CHAR) OR ## (NUM)
Placeholder Texts::  ▯▯
```

### Aircraft & Nav Ident Page
__________________________
|          IDENT         |
| MODEL        ENG RATING|      -click on side button, returns position
|XXXXXXXXXX  XXXXXXXXXXXX|
| NAV DATA         ACTIVE|
|AIRAC-1601 JAN07FEB03/16|
|                        |
|                        |
|                        |
|                        |
|                 DRAG/FF|
|               +0.0/+0.0|
|------------------------|
|<INDEX         POS INIT>|
|________________________|


Position Init
__________________________
|        POS INIT    1/3 |
|                LAST POS|
|      N48D36.1 W123D01.1|
| REF AIRPORT            |
|                        |
| GATE                   |
|                        |
| UTC             GPS POS|
|2030Z N48D36.2 W123D01.2|
|        SET INERTIAL POS|
|                        |
|------------------------|
|<INDEX            ROUTE>|
|________________________|



Route Init
__________________________
|      RTE 1         1/2 |
| ORIGIN             DEST|
|▯▯▯▯          ▯▯▯▯|
| RUNWAY          FLT NO |
|             -----------|
| ROUTE          CO ROUTE|
|<REQUEST     -----------|
|                        |
|                        |
| ROUTE -----------------|
|<SAVE              ALTN>|
|                        |
|<RTE 2         ACTIVATE>|
|________________________|
__________________________
|      RTE 1         1/2 |
| ORIGIN             DEST|
|KIAH                PHNL|
| RUNWAY          FLT NO |
|RW-----      -----------|
| ROUTE          CO ROUTE|
|<REQUEST     -----------|
|                        |
|                        |
| ROUTE -----------------|
|<SAVE              ALTN>|
|                        |
|<RTE 2         ACTIVATE>|
|________________________|


Route Legs
__________________________
|      RTE 1 LEGS    1/5 |
| 277°    158NM          |
|CRGER        ----/------|
| 280°    417NM          |
|EWM          ----/------|
| 278°     70NM          |
|DMN          ----/------|
| 277°     68NM          |
|WOBUG        ----/------|
| 279°    163NM          |
|PXR          ----/------|
|------------------------|
|<RTE 2 LEGS    ACTIVATE>|
|________________________|



DEPARTURE/ARRIVAL INDEX
__________________________
|      DEP/ARR INDEX     |
|        RTE 1           |
|<DEP      KIAH      ARR>|
|                        |
|          PHNL      ARR>|
|--------RTE 2 ----------|
|                        |
|                        |
|                        |
|------------------------|
|                        |
| DEP                 ARR|
|<----              ---->|
|________________________|

__________________________
|   KIAH DEPARTURES  1/5 |
| SIDS   RTE 1    RUNWAYS|
|SOMESID              RNW|
|       [SKIP]           |
|OTHERSID             RNW|
|                        |
|                        |
|                        |
|                        |
|                        |
|                        |
|------------------------|
|<INDEX            ROUTE>|
|________________________|

__________________________
|   KIAH DEPARTURES  1/5 |
| SIDS   RTE 1    RUNWAYS|
|SOMESI<SEL> <SEL>    33L|
| TRANS                  |
|CEW                     |
|                        |
|                        |
|                        |
|                        |
|                        |
|                        |
|------------------------|
|<INDEX            ROUTE>|
|________________________|


PREF INIT
__________________________
|       PREF INIT        |
| GR WT           CRZ ALT|
|###.#              FL###|
| FUEL         COST INDEX|
|###.#LB CALC          ##|
| ZFW       MIN FUEL TEMP|
|###.#              -## C|
| RESERVES         CRZ CG|
|###.#              ##.#%|
|PREF INIT      STEP SIZE|
|<REQUEST            RVSM|
|------------------------|
|<INDEX       THRUST LIM>|
|________________________|


THRUST LIM
__________________________
|       THRUST LIM       |
| SEL      OAT     TO  N1|
|---       15 C     ###.#|
|                        |
|<TO   <SEL><ARM>    CLB>|
| TO 1                   |
|<-10%             CLB 1>|
| TO 2                   |
|<-20%             CLB 2>|
|                        |
|                        |
|------------------------|
|<INDEX          TAKEOFF>|
|________________________|













--------UPLINK-------------


PREF INIT
__________________________
|       PREF INIT        |
| GR WT           CRZ ALT|
|###.#              FL###|
| FUEL         COST INDEX|
|###.#LB CALC          ##|
| ZFW       MIN FUEL TEMP|
|###.#              -## C|
| RESERVES         CRZ CG|
|###.#              ##.#%|
|     PREF INIT DATA     |
|<REJECT          ACCEPT>|
|------------------------|
|<INDEX       THRUST LIM>|
|PREF INIT UPLINK________|
