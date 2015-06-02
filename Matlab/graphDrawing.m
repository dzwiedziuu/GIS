%% Rysowanie wykresów symulowanego wyrza¿ania
% Ten skrypt ma za zadanie narysowaæ wykresy

%% Import biblioteki JAR
% Wymaga wykonania |'mvn assembly:single install'| na obu projektach
%
% * GraphColoringAlgorithm
% * GraphGenerator
%
gcaJarPath = [getenv('USERPROFILE'), '\.m2\repository\GraphColoringAlgorithm\GraphColoringAlgorithm\1.0.0\GraphColoringAlgorithm-1.0.0.jar'];
ggJarPath  = [getenv('USERPROFILE'), '\.m2\repository\GraphGenerator\GraphGenerator\1.0.0\GraphGenerator-1.0.0.jar']; 
ggOutFile  = [getenv('USERPROFILE'), '\Desktop\graph\graph.dat']; 
gcaOutFile = [getenv('USERPROFILE'), '\Desktop\graph\result.dat']; 

ggProbability = 0.9;
ggVertexNum   = 30;

gcaAlfa     = 0.99;
gcaTmpRatio = 50;
gcaKParam   = 20;

ggCmd = ['java -jar ' ggJarPath ' -p ' num2str(ggProbability,'%.2f') ' -v ' int2str(ggVertexNum) ' -of ' ggOutFile ];

% Wywo³anie generatora grafów
system(ggCmd);

num = 30;
data = zeros(num,4);
t_min = 1;
t_max = 10;
delta_t = (t_max - t_min) / (num - 1);
T = t_min:delta_t:t_max;
Tmin = T ./ gcaTmpRatio;
data(:,1) = T';
data(:,4) = Tmin';
for i = 1:num
    results = zeros(5,2);
    parfor j = 1:20
        gcaOutFileCur = [gcaOutFile int2str(j)];
        gcaCmd = ['java -jar ' gcaJarPath ' -a ' num2str(gcaAlfa, '%.4f') ' -i ' ggOutFile ' -o ' gcaOutFileCur ' -ti ' num2str(data(i,1), '%.4f') ' -tm ' num2str(data(i,4), '%.4f') ' -k ' int2str(gcaKParam)];
        system(gcaCmd);
        fileID = fopen(gcaOutFileCur,'r');
        formatSpec = '%f %f %f';
        out = fscanf(fileID,formatSpec);
        fclose(fileID);
        results(j,:) = out(1:2);
    end   
    data(i,2:3) = mean(results,1);
end

kolTitle = [ 'Graf n = ' int2str(ggVertexNum) ', p = ' num2str(ggProbability,'%.2f') ', k = ' int2str(gcaKParam) ', a = ' num2str(gcaAlfa) ', ratio = ' num2str(gcaTmpRatio) ];

figure;
scatter(data(:,1), data(:,3),'r');
title(kolTitle);
legend('Liczba skoków w górê');
xlabel('Temperatura pocz¹tkowa') % x-axis label
ylabel('Liczba skoków w górê') % y-axis label


figure;
scatter(data(:,1), data(:,2),'b');
title(kolTitle);
legend('Liczba kolorów');
xlabel('Temperatura pocz¹tkowa') % x-axis label
ylabel('Liczba kolorów') % y-axis label
