// golang unix socket client, connect to local jetty.sock, with http protocol.
package main

import (
	"context"
	"fmt"
	"net"
	"net/http"
	"net/url"
)

func main() {
	var unixAddr *net.UnixAddr
	unixAddr, _ = net.ResolveUnixAddr("unix", "/tmp/jetty.sock")
	// conn, _ := net.DialUnix("unix", nil, unixAddr)

	httpc := http.Client{
		Transport: &http.Transport{
			DialContext: func(_ context.Context, _, _ string) (net.Conn, error) {
				return net.DialUnix("unix", nil, unixAddr)
			},
		},
	}

	passwd := url.QueryEscape("cd#@!$%^&*()_+zz")
	fmt.Println(passwd)
	resp, err := httpc.Get("http://localhost/passwd?v=" + passwd)
	if err != nil {
		fmt.Println(err)
	}
	fmt.Println(resp)
}
