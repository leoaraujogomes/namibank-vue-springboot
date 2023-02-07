<template>
  <div
    style="
      pointer-events: none;
      position: fixed;
      width: 100%;
      height: 100%;
      top: 0px;
      left: 0px;
      z-index: 1000;
      display: flex;
    "
  >
    <v-card
      style="
        pointer-events: auto;
        align-items: center;
        padding: 10px;
        width: 400px;
        height: auto;
        margin: auto;
        background-color: #26a69a;
      "
      class="teal-lighten-3"
    >
      <v-card-actions>
        <v-text-field
          :rules="rules"
          style="color: white"
          clearable=""
          id="contaOrigem"
          label="Conta Origem"
          maxLength="6"
          onkeypress="return (event.charCode >= 48 && event.charCode <= 57)"
        ></v-text-field>
      </v-card-actions>
      <v-card-actions>
        <v-text-field
          :rules="rules"
          style="color: white"
          clearable=""
          id="contaDestino"
          label="Conta Destino"
          maxLength="6"
          onkeypress="return (event.charCode >= 48 && event.charCode <= 57)"
        ></v-text-field>
      </v-card-actions>
      <v-card-actions>
        <v-text-field
          :rules="rules"
          style="color: white"
          clearable=""
          id="valor"
          label="Valor"
          onkeypress="return (event.charCode >= 48 && event.charCode <= 57) || event.charCode == 46"
          prefix="$"
        ></v-text-field>
      </v-card-actions>

      <v-card-actions>
        <v-text-field
          :rules="rules"
          style="color: white"
          id="dtAgendamento"
          label="Data de agendamento"
          type="date"
        ></v-text-field>
      </v-card-actions>
      <v-radio-group
        style="color: white"
        color="white"
        inline
        label="Tipo de taxa:"
      >
        <v-radio
          v-bind:selected="radio"
          v-on:click="selecionaRadio('Data')"
          label="Data"
          value="1"
        ></v-radio>
        <v-radio
          v-bind:selected="radio"
          v-on:click="selecionaRadio('Valor')"
          label="Valor"
          value="2"
        ></v-radio>
      </v-radio-group>
      <div style="justify-content: center; display: flex">
        <v-dialog v-model="dialog">
          <template v-slot:activator="{ props }">
            <v-btn
              v-on:click="validaCampos"
              class="bg-teal-darken-2"
              v-bind="props"
            >
              Confirmar
            </v-btn>
          </template>
          <v-card style="width: 550px; height: 350px; margin: auto">
            <v-card-text v-html="mensagemPopup" id="textoPopup"></v-card-text>
            <v-card-actions>
              <v-btn color="primary" block @click="dialog = false"
                >Fechar</v-btn
              >
            </v-card-actions>
          </v-card>
        </v-dialog>
      </div>
    </v-card>
  </div>
</template>

<script>
import api from "../services/api";
import moment from "moment";
import { ref } from "vue";
export default {
  name: "Transferencia",
  data() {
    return {
      dialog: false,
      rules: [(value) => !!value || "Campo obrigatório."],
    };
  },
  setup() {
    const mensagemPopup = ref("");
    var radio = "";
    function selecionaRadio(radioSelecionado) {
      radio = radioSelecionado;
    }
    function validaCampos() {
      var dtAgendamentoConvert = moment(
        document.getElementById("dtAgendamento").value,
        "YYYY/MM/DD"
      );
      var dtHoje = moment(new Date()).startOf("day");
      var diferenca = dtAgendamentoConvert.diff(dtHoje, "days");
      if (
        document.getElementById("contaOrigem").value == "" ||
        document.getElementById("contaDestino").value == "" ||
        document.getElementById("valor").value == "" ||
        document.getElementById("dtAgendamento").value == "" ||
        radio == ""
      ) {
        mensagemPopup.value = "Preencha todos os campos antes de confirmar!";
      } else if (diferenca < 0) {
        mensagemPopup.value = "Insira uma data igual ou maior que hoje.";
      } else if (radio == "Valor") {
        sendTransf("D");
      } else if (diferenca == 0) {
        if (parseFloat(document.getElementById("valor").value) < 3.1) {
          mensagemPopup.value = "O valor mínimo para operações tipo A é R$3.10";
        } else {
          sendTransf("A");
        }
      } else if (diferenca <= 10) {
        if (parseFloat(document.getElementById("valor").value) < 3.1) {
          mensagemPopup.value =
            "O valor mínimo para operações tipo B é R$12.01";
        } else {
          sendTransf("B");
        }
      } else if (diferenca > 10) {
        if (parseFloat(document.getElementById("valor").value) < 0.1) {
          mensagemPopup.value = "O valor mínimo para operações tipo C é R$0.1";
        } else {
          sendTransf("C");
        }
      }
    }
    const sendTransf = (taxa) =>
      api
        .post(
          "/api/transferencia",
          {
            contaOrigem: document.getElementById("contaOrigem").value,
            contaDestino: document.getElementById("contaDestino").value,
            valorTransf: document.getElementById("valor").value,
            dataAgendamento: document.getElementById("dtAgendamento").value,
            tipoTransacao: taxa,
          },
          {
            headers: {
              "Access-Control-Allow-Origin": "*",
              "Access-Control-Allow-Methods":
                "GET,PUT,POST,DELETE,PATCH,OPTIONS",
            },
          }
        )
        .then((response) => {
          if (response.status == 200) {
            mensagemPopup.value =
              `<h3>Agendamento de transferência realizado com sucesso!</h3><br/>` +
              `<h4>RECIBO:</h4><br/>` +
              `<strong>Conta Origem</strong>: ${response.data.contaOrigem}<br/>` +
              `<strong>Conta Destino</strong>: ${response.data.contaDestino}<br/>` +
              `<strong>Valor Transferido</strong>: R$ ${response.data.valorTransf}<br/>` +
              `<strong>Taxa Paga</strong>: R$ ${response.data.taxaTransf}<br/>` +
              `<strong>Data do Agendamento</strong>: ${response.data.dataAgendamento}`;
          }
        })
        .catch((error) => {
          mensagemPopup.value = error.response.data.message;
        });
    return {
      sendTransf,
      validaCampos,
      selecionaRadio,
      mensagemPopup,
      radio,
    };
  },
};
</script>
