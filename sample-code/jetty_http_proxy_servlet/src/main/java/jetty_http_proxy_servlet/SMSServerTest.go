package main

import (
	"fmt"
	"io"
	"io/ioutil"
	"log"
	"net/http"
    "encoding/json"
)


type Data struct{
 Code int `json:"code"`
 Errmsg string `json:"errmsg"`
}

func main() {
	// Set routing rules
	http.HandleFunc("/", Tmp)

	//Use the default DefaultServeMux.
	log.Println("SMS Server started.")
	err := http.ListenAndServe(":58888", nil)
	if err != nil {
		log.Fatal(err)
	}
}

func Tmp(w http.ResponseWriter, r *http.Request) {
	b, err := ioutil.ReadAll(r.Body)
	fmt.Printf("request: url=%v, body= %v, err= %v \n", r.URL, string(b), err)

    ret := &Data{0, ""}
    ret_json,_ := json.Marshal(ret)
    io.WriteString(w, string(ret_json))
}
