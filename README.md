# Wind Charge Plugin
Plugin ultilizado para alterar as propriedades do windcharge, velocidade, força e particulas adicionais, plugin simples com comandos fáceis de lembrar

## Download

Caso deseje baixar apenas o Plugin para uso [`WINDCHARGECONTROL`](https://github.com/R4NP3R/WindChargePlugin/tree/main/build/libs).

## Comandos

* /windcharge help - retorna todos os comandos do Plugin
* /windcharge velocity/speed <"speed"> - altera a velocidade do windcharge
* /windcharge power <"strength"> - altera a força do windcharge
* /windcharge velocity/speed - retorna o valor atual de velocidade do windcharge
* /windcharge power - retorna o valor atual de velocidade do windcharge
* /windcharge particles list - lista as particulas disponiveis para uso
* /windcharge particles off - desativa as particulas do comando
* /windcharge particles <"particle"> - para selecionar a particula desejada



## Scripts

Caso deseje usar um script para facilitar na hora de enviar o plugin para pasta do seu servidor use o [`CopyJar`](https://github.com/R4NP3R/WindChargePlugin/blob/main/build.gradle), lá no build.gradle apenas seleciona a pasta onde deseja enviar o mod, Exemplo:

```sh
task copyJar {
    copy {
        from 'build/libs/WindChargeControl-1.0.jar'
        into 'C:/Users/{USER}/{local}/server/plugins'
    }
}

```




