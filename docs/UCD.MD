# Use Case Diagram (UCD)

![Use Case Diagram](/docs/UCD.svg)


**For each UC/US, it must be provided evidences of applying main activities of the software development process (requirements, analysis, design, tests and code). Gather those evidences on a separate file for each UC/US and set up a link as suggested below.**

# Use Cases / User Stories
| UC/US  | Description                                                               |                   
|:----|:------------------------------------------------------------------------|
| US101 | [As a traffic manager, I wish to import ships from a text file into a BST.](US101.md)   |
| US102 | [As a traffic manager I wish to search the details of a ship using any of its codes: MMSI, IMO or Call Sign.](US102.md)  |
| US103 | [As a traffic manager I wish to have the positional messages temporally organized and associated with each of the ships ](US103.md)|
| US104 | [As a traffic manager I wish to make a Summary of a ship's movements.](US104.md)|
| US105 | [As a traffic manager I wish to list for all ships the MMSI, the total number of movements, Travelled Distance and Delta Distance](US105.md)|
| US106 | [As a traffic manager I want to get the top-N ships with the most kilometres travelled and their average speed (MeanSOG)](US106.md)|
| US107 | [As a traffic manager, I want to return pairs of ships with routes with close departure/arrival coordinates (no more than 5 Kms away) and with different Travelled Distance.](US107.md)|
| US108 | [As Project Manager, I want the team to develop the data model required to support all the functionality and to fulfill the purpose of the system to develop. This data model is to be designed following a systematic data modeling methodology. ](US108.md)|
| US109 | [As Project Manager, I want the team to draft an SQL script to test whether the database verifies all the data integrity restrictions that are required to fulfil the purpose of the system and the business constraints of the UoD.](US109.md)|
| US110 | [As Project Manager, I want the team to define the naming conventions to apply when defining identifiers or writing SQL or PL/SQL code. The naming conventions may evolve as new database and programming objects are known. The naming conventions guide should be organized in a way to facilitate its maintenance.](US110.md)|
| US111 | [As Project Manager, I want the team to create a SQL script to load the database with a minimum set of data sufficient to carry out data integrity verification and functional testing. This script shall produce a bootstrap report providing the number of tuples/rows in each relation/table.](US111.md)|