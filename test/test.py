# ini file untuk testing apakah flask bisa atau tidak
# bukan buat acuan nanti di android
# android tinggal ngirim data post ke server

import requests

url = "https://getresponse-4abxtq7oia-as.a.run.app/"
# url = "http://localhost:5000/"

resp = requests.post(url, data ={"chat": "tugas sudah selesai"})

print(resp.json())