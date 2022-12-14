#!/usr/bin/env bash

# 실행 유저 확인
echo "[INFO] master_node.sh, current user: $(whoami)"

# init kubernetes
kubeadm init --token 123456.1234567890123456 --token-ttl 0 \
  --pod-network-cidr=172.16.0.0/16 --apiserver-advertise-address=192.168.10.10 \
  --cri-socket=unix:///run/containerd/containerd.sock

# config for master node only
mkdir -p $HOME/.kube
cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
chown $(id -u):$(id -g) $HOME/.kube/config

# config for kubernetes's network
kubectl apply -f https://raw.githubusercontent.com/projectcalico/calico/v3.24.5/manifests/calico.yaml

# enable strict ARP mode
kubectl get configmap kube-proxy -n kube-system -o yaml |
  sed -e "s/strictARP: false/strictARP: true/" |
  kubectl diff -f - -n kube-system
kubectl get configmap kube-proxy -n kube-system -o yaml |
  sed -e "s/strictARP: false/strictARP: true/" |
  kubectl apply -f - -n kube-system

# 생성
# metallb-system 네임스페이스 생성, 파드(컨트롤러, 스피커) 생성, RBAC(서비스/파드/컨피그맵 조회 등등 권한들) 생성
kubectl apply -f https://raw.githubusercontent.com/metallb/metallb/v0.12.1/manifests/namespace.yaml
kubectl apply -f https://raw.githubusercontent.com/metallb/metallb/v0.12.1/manifests/metallb.yaml

# create config
cat <<EOF | kubectl create -f -
apiVersion: v1
kind: ConfigMap
metadata:
  namespace: metallb-system
  name: config
data:
  config: |
    address-pools:
    - name: metallb-ip-range
      protocol: layer2
      addresses:
      - 192.168.10.21-192.168.10.99
EOF

# install bash-completion for kubectl
rm -rfv /var/lib/apt/lists/*
apt-get update
apt-get install -y bash-completion

# kubernetes bash completion 시스템 전체에 적용
kubectl completion bash | sudo tee /etc/bash_completion.d/kubectl >/dev/null

# alias kubectl to k
echo 'alias k=kubectl' >>~/.bashrc
echo 'complete -o default -F __start_kubectl k' >>~/.bashrc

