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
      display: grid;
    "
  >
    <v-card
      style="
        pointer-events: auto;
        align-items: center;
        padding: 10px;
        width: 400px;
        height: 300px;
        margin-top: auto;
        margin-left: auto;
        margin-right: auto;
        overflow-y: auto;
        background-color: #26a69a;
      "
    >
      <v-card-actions
        v-for="(item, index) in listaHistorico"
        :key="item.id"
        :id="item.id"
        style="color: white"
      >
        <div class="text-center">
          <v-btn v-on:click="getTransfById(index + 1)">
            {{ item.dataEfetuado }}
          </v-btn>
        </div>
      </v-card-actions>
    </v-card>
    <v-card
      style="
        pointer-events: auto;
        align-items: center;
        padding: 10px;
        width: 400px;
        height: 175px;
        margin-top: 0px;
        margin-left: auto;
        margin-right: auto;
        overflow-y: auto;
        background-color: #00695c;
      "
    >
      <v-card-text
        v-html="transferenciaHistorico"
        style="color: white"
      ></v-card-text>
    </v-card>
  </div>
</template>

<script>
import SideBar from "./SideBar.vue";
import api from "../services/api";
import { ref, onMounted } from "vue";
export default {
  components: {
    SideBar,
  },
  setup() {
    const listaHistorico = ref([]);
    const transferenciaHistorico = ref();
    var detalheTransacao = "";
    const getTransf = () =>
      api.get("/api/transferencia").then((response) => {
        if (response.status == 200) {
          listaHistorico.value = response.data;
        }
      });
    const getTransfById = (id) =>
      api.get(`/api/transferencia/${id}`).then((response) => {
        if (response.status == 200) {
          transferenciaHistorico.value =
            `Conta Origem: ${response.data.contaOrigem} <br/>` +
            `Conta Destino: ${response.data.contaDestino} <br/>` +
            `Valor Transferido: R$ ${response.data.valorTransf} <br/>` +
            `Taxa Paga: R$ ${response.data.taxaTransf} <br/>` +
            `Data do Agendamento: ${response.data.dataAgendamento} <br/>` +
            `Data Efetuado: ${response.data.dataEfetuado} <br/>`;
          detalheTransacao = `Conta Origem: ${transferenciaHistorico.value}`;
        }
      });
    onMounted(getTransf);
    return {
      getTransf,
      getTransfById,
      listaHistorico,
      transferenciaHistorico,
      detalheTransacao,
    };
  },
};
</script>
