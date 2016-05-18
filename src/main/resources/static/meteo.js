
$(function() {

    var startTime, endTime;

    initPlot();
    getData();

    setInterval(function() {
        getData();
    }, 30000); //each 30s




    function getData() {
        getTime();
        $.getJSON('readouts/1/' + getFormattedDate(startTime) + "/" + getFormattedDate(endTime), function(data) {
            var temp = [],
                voltage = [],
                chart;
            data.forEach(function(readout) {
                temp.push([readout.timestamp, readout.value]);
                voltage.push([readout.timestamp, readout.voltage.value]);
            });
            chart = $('#container').highcharts();
            chart.series[0].setData(temp);
            chart.series[1].setData(voltage);
        });
    }

    function getFormattedDate(date) {
        return String(date.getFullYear()) +
            ('00' + String(date.getMonth() + 1)).slice(-2) +
            ('00' + String(date.getDate())).slice(-2) +
            'T' +
            ('00' + String(date.getHours())).slice(-2) +
            ('00' + String(date.getMinutes())).slice(-2) +
            ('00' + String(date.getSeconds())).slice(-2) +
            'Z';

    }

    function getTime() {
        //last 24H
        startTime = new Date();
        startTime = new Date(startTime.getTime() - (24 * 1000 * 60 * 60));
        endTime = new Date();
    }

    function initPlot() {

        Highcharts.setOptions({
            global: {
                //useUTC: false,
                timezoneOffset: -120
            }
        });

        $('#container').highcharts({
            credits: {
                enabled: false
            },
            plotOptions: {
                line: {
                    marker: {
                        enabled: false
                    }
                }
            },
            chart: {
                zoomType: "x"
            },
            title: {
                text: 'Temperature and voltage',
                x: -20 //center
            },
            subtitle: {
                text: 'Last 24H',
                x: -20
            },
            xAxis: {
                type: 'datetime'
            },
            yAxis: {
                title: {
                    text: 'Values'
                }
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0
            },
            series: [{
                tooltip: {
                    valueSuffix: '°C'
                },
                name: 'Temperature (°C)',
                data: []
            }, {
                tooltip: {
                    valueSuffix: 'V'
                },
                name: 'Voltage (V)',
                data: []
            }]

        });

    }


});
