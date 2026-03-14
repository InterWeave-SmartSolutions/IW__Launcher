DROP TABLE IF EXISTS `hostedprofiles`.`userprofiles`;
CREATE TABLE `hostedprofiles`.`userprofiles` (
  `Id` int(10) unsigned NOT NULL auto_increment,
  `FirstName` varchar(45) NOT NULL default '',
  `LastName` varchar(45) NOT NULL default '',
  `Password` text NOT NULL,
  `Company` varchar(255) default NULL,
  `Title` varchar(255) default NULL,
  `Email` varchar(255) NOT NULL default '',
  `LastLogin` timestamp NOT NULL default '0000-00-00 00:00:00',
  `NumberOfLogins` int(10) unsigned zerofill default '0000000000',
  `Created` timestamp NOT NULL default '0000-00-00 00:00:00',
  `Modified` timestamp NOT NULL default '0000-00-00 00:00:00',
  `LisOfFlowId` text,
  `ListOfQueryId` text,
  PRIMARY KEY  (`Id`),
  UNIQUE KEY `Email_Index` USING HASH (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `hostedprofiles`.`companies`;
CREATE TABLE `hostedprofiles`.`companies` (                                 
             `Id` int(11) NOT NULL auto_increment,                    
             `Name` varchar(255) NOT NULL default '',                 
             `Configuration` longtext,                                
             `Created` timestamp NULL default '0000-00-00 00:00:00',  
             `Modified` timestamp NULL default CURRENT_TIMESTAMP,     
             `Administrator` varchar(255) NOT NULL default '',        
             `Password` varchar(255) NOT NULL default '',             
             `Token` varchar(255) NOT NULL default '',                
             `IsPaid` tinyint(1) NOT NULL default '0',                
             `FirstName` varchar(80) default NULL,                    
             `LastName` varchar(80) default NULL,                     
             `SolutionType` varchar(80) NOT NULL default '',          
             `FlowList` longtext,                                     
             `QueryList` longtext,          
             PRIMARY KEY  (`Id`)                                      
           ) ENGINE=MyISAM DEFAULT CHARSET=latin1 