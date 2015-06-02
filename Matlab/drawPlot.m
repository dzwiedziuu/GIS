figure;
scatter(data(:,1), data(:,3),'r');
title('Liczba skoków w górê');
legend('Liczba skoków w górê');
xlabel('Temperatura pocz¹tkowa') % x-axis label
ylabel('Liczba skoków w górê') % y-axis label


figure;
scatter(data(:,1), data(:,2),'b');
title('Liczba kolorów');
legend('Liczba kolorów');
xlabel('Temperatura pocz¹tkowa') % x-axis label
ylabel('Liczba kolorów') % y-axis label
