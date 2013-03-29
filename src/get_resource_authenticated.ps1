$Url = "http://skillport.books24x7.com/toc.aspx?bkid=50801"
$Path = "D:\_SRC\_PROG\_INTELLIJ\html_fetcher\src\temp.html"
$Username = ""
$Password = ""
 
$WebClient = New-Object System.Net.WebClient
$WebClient.Credentials = New-Object System.Net.Networkcredential($Username, $Password)
$WebClient.DownloadFile( $url, $path )