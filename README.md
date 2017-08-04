# ScalaMaven
Spring boot2 + webflux + Jpa + scala


# Unable to start Netty (TimeoutException)
```bash
sudo sed -i bak "s^127\.0\.0\.1.*^127.0.0.1 localhost $(hostname)^g" /etc/hosts
sudo sed -i bak "s^::1.*^::1 localhost $(hostname)^g" /etc/hosts
sudo ifconfig en0 down
sudo ifconfig en0 up
```