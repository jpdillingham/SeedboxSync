<h1><img src="http://jpdillingham.github.io/images/seedboxsync.png" height="24">SeedboxSync</h1>

[![Build status](https://travis-ci.org/jpdillingham/SeedboxSync.svg?branch=master)](https://travis-ci.org/jpdillingham/SeedboxSync)
[![codecov](https://codecov.io/gh/jpdillingham/SeedboxSync/branch/master/graph/badge.svg)](https://codecov.io/gh/jpdillingham/SeedboxSync)
[![Dependency Status](https://www.versioneye.com/user/projects/581c03d24304530b002f8ae2/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/581c03d24304530b002f8ae2)
[![License: GPL v3](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](https://github.com/jpdillingham/SeedboxSync/blob/master/LICENSE)

A Java console application for syncing files with a seedbox account via FTP.

In reality it can be used to do a two-way synchronization for any FTP account, but it was created with seedboxes in mind.

# Prepare

Configure the torrent client from your seedbox provider to move completed downloads to a specific folder upon completion, and to monitor a folder
for new torrent files.  The configuration for the ruTorrent is shown below as an example.

![ruTorrent Example](http://jpdillingham.github.io/images/rutorrent-setup.PNG)

Your client settings may differ.

# Install

This application requires the [Java JRE 8](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html), so you'll need to download and
install that first.

Download the latest release from the Releases tab above and place the file in a folder on the host machine.  You'll create a configuration file, and the application
creates a database file and a log directory for log files, all stored in the root directory.

# Configure

Choose (and/or create) two folders: one to contain downloaded files and another to contain files to upload.  You'll also want to gather the login information
for your seedbox account.

Using your favorite text editor, create a file named ```config.json``` and paste the following text into it:

```
{
	"server": "[server address]",
	"port": [port, probably 21],
	"username": "[your username]",
	"password": "[your password]",
	"interval": [number of seconds between synchronizations, in seconds],
	"remoteDownloadDirectory": "[path to the completed downloads folder on your FTP]",
	"localDownloadDirectory": "[path to your chosen downloads folder]",
	"remoteUploadDirectory": "[path to the watch folder on your FTP]",
	"localUploadDirectory": "[path to your chosen upload folder]"
}
```

Replace the values in brackets with your settings.

# Launch

From a command prompt, issue the command ```java -jar SeedboxSync-XXX.jar``` (where XXX is the current version) to launch the application.

![SeedboxSync Startup](http://jpdillingham.github.io/images/seedboxsync-startup.PNG)

# Sync

To download a new torrent automatically, save the .torrent file to the local upload directory.  The application will upload the file on the next synchronization and the torrent
client running on your seedbox should add it and start the download.  When the download is finished the client should move the files to the completed folder and the application
will locate and download the files to your local download directory the next time the synchronization is executed.