package main

import (
	"io"
	"log"
	"net"
)

func main() {
	port := ":18000"
	tcpAddr, err := net.ResolveTCPAddr("tcp4", port)
	checkError(err)
	log.Println("Socket Server Started at :18000...")
	listener, err := net.ListenTCP("tcp", tcpAddr)
	checkError(err)
	for {
		conn, err := listener.Accept()
		checkError(err)
		// log.Printf("=> conection from: %s", conn.RemoteAddr().String())
		// handle a conn in a goroutine
		go handler(conn)
	}

}

func checkError(err error) {
	if err != nil {
		log.Printf("error: %s", err.Error())
	}
}

func handler(conn net.Conn) {
	tmp := make([]byte, 1024)

		n, err := conn.Read(tmp)
		if err != nil {
			if err != io.EOF {
				log.Printf("unexpected read error: %s", err)
			}
		}		
		log.Printf("%s: %s", conn.RemoteAddr().String(), tmp[:n])	
	
	conn.Close()

}



